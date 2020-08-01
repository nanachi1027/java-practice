package com.mae.java.dynamicproxy.support;

import com.mae.java.dynamicproxy.IObjectInvoker;
import com.mae.java.dynamicproxy.util.ProxyUtil;

import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class AbstractInvoker implements IObjectInvoker, Serializable {
    @Override
    public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
        if (ProxyUtil.isHashCodeMethod(method)) {
            return Integer.valueOf(System.identityHashCode(proxy));
        }

        if (ProxyUtil.isEqualsMethod(method)) {
            return Boolean.valueOf(proxy == arguments[0]);
        }

        // let's give logic implementation to invokeHandler
        // and invokeHandler will give this implementation to sub-classes
        return invokeHandler(proxy, method, arguments);
    }

    public abstract Object invokeHandler(Object proxy, Method method, Object ... arguments) throws Throwable;
}
