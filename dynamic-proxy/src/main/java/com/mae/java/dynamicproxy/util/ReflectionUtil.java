package com.mae.java.dynamicproxy.util;

import com.google.common.base.Optional;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionUtil {
    /**
     * Extract method instance from given instance, by passing method name and method's parameter list
     *
     * @param object class instance we extract method from
     * @param methodName name fot he method
     * @param parameterTypes parameter list of the method
     * @return method instance
     *
     * 1. input param validation
     * 2. traverse all declared methods in given object's class and parent classes
     * 3. check method name equals, parameter type list equals match, return method instance
     * 4. not match continue cycle.
     */
    public static Method getMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        if (null == object || methodName == null || methodName.isEmpty()) {
            return null;
        }

        for (Class<?> cls = object.getClass(); hasParentClass(cls); ) {
            for (Method method : cls.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                    return method;
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static Method getMethod(Class<?> cls, String methodName, Class<?>[] parameterTypes) {
        if (null == cls || methodName == null || methodName.length() == 0) {
            return null;
        }

        for (Class<?> iter = cls; hasParentClass(cls); ) {
            for (Method method : iter.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                    return method;
                }
            }
            iter = iter.getSuperclass();
        }
        return null;
    }

    /**
     * Method to create new instance by passing clazz via its default constructor
     *
     * @param clazz
     * @return instance of the given clazz
     *
     * 1. get all constructor from the clazz
     * 2. check out whether default (no param) constructor exists
     * 3. not exists throws RuntimeException
     * 4. else call constructor create instance and return
     */
    public static <T> T newInstance(Class<?> clazz) {
        Constructor<?> [] cons = getAllConstructorsOfClass(clazz, true);

        if (!ArrayUtils.isNotEmpty(cons)) {
            return null;
        }

        Optional<? extends Constructor<?>> optionalCon = getDefaultConstructor(cons);
        if (!optionalCon.isPresent()) {
            throw new RuntimeException(String.format("Class %s doesn't get default constructor", clazz.getName()));
        }

        try {
            T instance = (T) optionalCon.get().newInstance();
            return instance;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to get all constructors
     *
     * @param clazz      from which class get all the constructors
     * @param accessible get the constructors that are only accessible or both
     *                   <p>
     *                   1. validate the class is not empty
     *                   2. get declared constructors
     *                   3. refresh construcors accessibility
     *                   4. return constructor arr
     */
    public static Constructor<?>[] getAllConstructorsOfClass(final Class<?> clazz, boolean accessible) {
        if (clazz == null) {
            return null;
        }

        Constructor<?>[] conArr = clazz.getDeclaredConstructors();

        if (ArrayUtils.isNotEmpty(conArr)) {
            AccessibleObject.setAccessible(conArr, accessible);
        }

        return conArr;
    }

    /**
     * Method to get default constructor
     * @param conArr array of constructors
     * @return Optional set
     *
     * 1. make sure conArr not empty
     * 2. traverse all constructor and get no param one return -- the one this method searches for
     * 3. no default constructor return Optional default value
     */
    public static Optional<? extends Constructor<?>> getDefaultConstructor(Constructor<?> [] conArr) {
        if (!ArrayUtils.isNotEmpty(conArr)) {
            return Optional.absent();
        }

        for (Constructor<?> con : conArr) {
            if (!ArrayUtils.isNotEmpty(conArr.getClass().getTypeParameters())) {
                return Optional.of(con);
            }
        }

        return Optional.absent();
    }


    /**
     * Implement a method justify whether input Class has parent class
     *
     * @param cls
     * @return has parent class return true, else return false
     */
    public static boolean hasParentClass(Class<?> cls) {
        return cls != null && !cls.equals(Object.class);
    }
}
