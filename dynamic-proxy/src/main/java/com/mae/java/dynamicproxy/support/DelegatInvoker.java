package com.mae.java.dynamicproxy.support;

import com.mae.java.dynamicproxy.IObjectProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DelegatInvoker extends AbstractInvoker {
    private static final long serialVersionUID = 3849050493894L;

    private final IObjectProvider<?> objProvider;

    public DelegatInvoker(IObjectProvider<?> objProvider) {
        this.objProvider = objProvider;
    }

    @Override
    public Object invokeHandler(Object proxy, Method method, Object... arguments) throws Throwable {
        try {
            return method.invoke(objProvider.getObject(), arguments);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
