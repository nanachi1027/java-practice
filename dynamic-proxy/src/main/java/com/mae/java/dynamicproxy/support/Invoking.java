package com.mae.java.dynamicproxy.support;

import com.mae.java.dynamicproxy.IObjectInvoker;

import java.lang.reflect.Method;

public class Invoking extends AbstractInvoker {

    private static final Long serialVersionUID = 32749434574839847L;

    private final IObjectInvoker invoker;

    public Invoking(IObjectInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invokeHandler(Object proxy, Method method, Object... arguments) throws Throwable {
        return invoker.invoke(proxy, method, arguments);
    }
}
