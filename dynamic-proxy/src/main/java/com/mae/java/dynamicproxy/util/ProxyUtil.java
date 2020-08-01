package com.mae.java.dynamicproxy.util;

import com.google.common.collect.Maps;
import com.mae.java.dynamicproxy.IProxyBuilder;
import com.mae.java.dynamicproxy.ProxyBuilderDefaultImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ProxyUtil {
    public static final Object [] EMPTY_ARGUMENTS = Emptys.EMPTY_OBJECT_ARR;
    public static final Class<?> [] EMPTY_ARGUMENT_TYPE = Emptys.EMPTY_CLASS_ARR;
    private static final Map<Class<?>, Class<?>> WRAPPER_CLASS_MAP;
    private static final Map<Class<?>, Object> NULL_VALUE_MAP;

    // here we init the wrapper class map  type & class
    // and the null value map type & empty value
    static {
        Map<Class<?>, Class<?>> tempWrapperMap = Maps.newHashMap();
        tempWrapperMap.put(Byte.TYPE, Byte.class);
        tempWrapperMap.put(Character.TYPE, Character.class);
        tempWrapperMap.put(Short.TYPE, Short.class);
        tempWrapperMap.put(Integer.TYPE, Integer.class);
        tempWrapperMap.put(Long.TYPE, Long.class);
        tempWrapperMap.put(Double.TYPE, Double.class);
        tempWrapperMap.put(Boolean.TYPE, Boolean.class);

        WRAPPER_CLASS_MAP = Collections.unmodifiableMap(tempWrapperMap);

        Map<Class<?>, Object> tempNullMap = Maps.newHashMapWithExpectedSize(tempWrapperMap.size());
        tempNullMap.put(Byte.TYPE, Emptys.BYTE_ZERO);
        tempNullMap.put(Short.TYPE, Emptys.SHORT_ZERO);
        tempNullMap.put(Integer.TYPE, Emptys.INTEGER_ZERO);
        tempNullMap.put(Long.TYPE, Emptys.LONG_ZERO);
        tempNullMap.put(Boolean.TYPE, Emptys.BOOL_ZERO);
        tempNullMap.put(Float.TYPE, Emptys.FLOAT_ZERO);
        tempNullMap.put(Character.TYPE, Emptys.CHAR_ZERO);

        NULL_VALUE_MAP = Collections.unmodifiableMap(tempNullMap);
    }

    /**
     * Get class's name by given Class
     * If Class is an array type needs append '[]'
     *
     * @param clazz instance
     * @return name of the clazz instance
     */
    public static String getJavaClassName(Class<?> clazz) {
        if (clazz.isArray()) {
            return clazz.getComponentType() + "[]";
        }
        return getWrapperClass(clazz).getName();
    }


    /**
     * Get wrapper class by given primitive type
     * @param primitiveType
     * @return primitiveType's wrapper class
     */
    public static Class<?> getWrapperClass(Class<?> primitiveType) {
        if (!primitiveType.isPrimitive()) {
            return null;
        }
        return WRAPPER_CLASS_MAP.get(primitiveType);
    }

    /**
     * Get null value by given primitive type
     * @param primitiveType
     * @return primitiveType's null value
     */
    public static <T> T getNullValue(Class<T> primitiveType) {
        if (!primitiveType.isPrimitive()) {
            return null;
        }
        return (T) NULL_VALUE_MAP.get(primitiveType);
    }

    public static IProxyBuilder getProxyBuilderInstance() {
        return ProxyBuilderDefaultImpl.INSTANCE;
    }

    public static boolean isHashCodeMethod(Method method) {
        return method.getName().equals("hashCode") &&
                method.getParameterTypes().length == 0 &&
                method.getReturnType().equals(Integer.TYPE);
    }

    public static boolean isEqualsMethod(Method method) {
        return method.getName().equals("equals") &&
                method.getParameterTypes().length == 1 &&
                method.getParameterTypes()[0].equals(Object.class) &&
                method.getReturnType().equals(Boolean.TYPE);
    }
}
