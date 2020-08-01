package com.mae.java.dynamicproxy;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mae.java.dynamicproxy.util.Pair;
import com.mae.java.dynamicproxy.util.ReflectionUtil;

public class MethodSignatureHandler {

    private static final long serialVersionUID = 45678945678956789L;
    // Key: Class<?>, Value: class name in short
    private static final Map<Class<?>, Character> primitiveAbbreviations;
    // revers of primitiveAbbreviations
    private static final Map<Character, Class<?>> reverseAbbreviations;
    // internal string
    private final String internal;


    static {
        // init two map
        Map<Class<?>, Character> map = Maps.newHashMap();

        // java basic type
        map.put(Boolean.TYPE, Character.valueOf('Z'));
        map.put(Byte.TYPE, Character.valueOf('B'));
        map.put(Short.TYPE, Character.valueOf('S'));
        map.put(Integer.TYPE, Character.valueOf('I'));
        map.put(Character.TYPE, Character.valueOf('C'));
        map.put(Long.TYPE, Character.valueOf('J'));
        map.put(Float.TYPE, Character.valueOf('F'));
        map.put(Double.TYPE, Character.valueOf('D'));
        map.put(Void.TYPE, Character.valueOf('V'));

        Map<Character, Class<?>> reverseMap = Maps.newHashMapWithExpectedSize(map.size());
        for (Map.Entry<Class<?>, Character> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }

        primitiveAbbreviations = Collections.unmodifiableMap(map);
        reverseAbbreviations = Collections.unmodifiableMap(reverseMap);
    }

    public MethodSignatureHandler(Method method) {
        final StringBuilder sb = new StringBuilder(method.getName()).append('(');
        for (Class<?> param : method.getParameterTypes()) {
            appendTo(sb, param);
        }
        sb.append(')');
        this.internal = sb.toString();
    }

    public static String getMethodName(String clsName, String methodName, Class<?>[] paramTypes) {
        final StringBuilder buf = new StringBuilder(clsName).append('.').append(methodName).append('(');
        if (paramTypes != null && paramTypes.length > 0) {
            for (Class<?> param : paramTypes) {
                buf.append(param.getName()).append(',');
            }
        }
        buf.append(')');
        return buf.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodSignatureHandler that = (MethodSignatureHandler) o;
        return that.internal.equals(internal);
    }

    @Override
    public int hashCode() {
        return internal.hashCode();
    }

    @Override
    public String toString() {
        return internal;
    }

    // get Method instance by giving Class type
    // method signature in string and in class type
    public Method toMethod(Class<?> type) {
        final Pair<String, Class<?>[]> info = parse(internal);
        return ReflectionUtil.getMethod(type, info.getKey(), info.getValue());
    }

    private static void appendTo(StringBuilder buf, Class<?> type) {
        if (type.isPrimitive()) {
            // java primitive
            buf.append(primitiveAbbreviations.get(type));
        } else if (type.isArray()) {
            // array (begin with [)
            buf.append('[');
            appendTo(buf, type.getComponentType());
        } else {
            // reference
            buf.append('L').append(type.getName().replace('.', '/')).append(';');
        }
    }

    /**
     * inner static class provides methods to record MethodSignature parse position
     */
    private static class MethodSignaturePosition extends ParsePosition {
        MethodSignaturePosition() {
            super(0);
        }

        // move 1 byte
        MethodSignaturePosition next() {
            return plus(1);
        }

        MethodSignaturePosition plus(int addValue) {
            setIndex(getIndex() + addValue);
            return this;
        }
    }

    private static Pair<String, Class<?>[]> parse(String internal) {
        MethodSignaturePosition position = new MethodSignaturePosition();
        int paramBegin = internal.indexOf('(', position.getIndex());

        String methodName = internal.substring(0, paramBegin).trim();

        List<Class<?>> paramList = Lists.newArrayList();

        while (position.getIndex() < internal.length()) {
            // get current char value
            char c = internal.charAt(position.getIndex());
            if (Character.isWhitespace(c)) {
                position.next();
                continue;
            }
            Character k = Character.valueOf(c);
            // transfer char to class type by reverseAbbreviations
            if (reverseAbbreviations.containsKey(k)) {
                paramList.add(reverseAbbreviations.get(k));
                position.next();
                continue;
            }

            // end of method signature in string
            if (')' == c) {
                position.next();
                break;
            }
            try {
                // type not belong to java primary type
                paramList.add(parseType(internal, position));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(String.format("Failed to parse methods signature: %s", internal));
            }
        }
        return new Pair<String, Class<?>[]>(methodName, paramList.toArray(new Class[0]));
    }

    private static Class<?> parseType(String internal, MethodSignaturePosition position) throws ClassNotFoundException {
        int currIndex = position.getIndex();
        char currChar = internal.charAt(currIndex);

        switch (currChar) {
            case '[':
                // array , which type needs recursive call parseType
                position.next();
                Class<?> componentType = parseType(internal, position);
                return Array.newInstance(componentType, 0).getClass();
            case 'L':
                // class type, usually reference to class object
                // get canonical class name
                position.next();
                int clsNameBegin = position.getIndex();
                int clsNameEnd = internal.indexOf(';', clsNameBegin);

                String canonicalClsName = internal.substring(clsNameBegin, clsNameEnd).replace('/', '.');

                position.setIndex(clsNameEnd + 1);
                return Class.forName(canonicalClsName);

            default:
                // not java primary, not array and class type, throw exception
                throw new IllegalArgumentException(String.format("Failed to parse method signature: %s at position %d",
                        internal, currIndex));
        }
    }
}
