package com.mae.java.dynamicproxy;

import java.lang.reflect.Method;

/**
 * 定义了反射调用的基本方法, 及反射调用元素的访问方式.
 * 其中 proceed() 方法会触发 Method#invoke(Object target, Object ... params)
 * 方法的调用以此来实现方法的反射调用.
 */
public interface IInvocation {
    Object [] getArgs();

    Method getMethod();

    Object getProxy();

    Object proceed() throws Throwable;
}
