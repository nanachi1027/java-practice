package com.mae.java.dynamicproxy;

import java.lang.reflect.Method;

public interface IInvocation {
    Object [] getArgs();

    Method getMethod();

    Object getProxy();

    Object proceed() throws Throwable;
}
