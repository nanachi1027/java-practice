package com.mae.java.dynamicproxy.support;

import com.mae.java.dynamicproxy.IInterceptor;

import java.lang.reflect.Method;

public class InterceptInvoer extends AbstractInvoker {

    private final static long serialVersionUID = 240407538479583749L;

    private final IInterceptor interceptor;
    private final Object target;

    public InterceptInvoer(IInterceptor interceptor, Object target) {
        this.interceptor = interceptor;
        this.target = target;
    }

    @Override
    public Object invokeHandler(Object proxy, Method method, Object... arguments) throws Throwable {
        ReflectionInvocation reflectionInvocation = new ReflectionInvocation(target, proxy, method, arguments);
        return interceptor.intercept(reflectionInvocation);
    }
}
