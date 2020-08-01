package com.mae.java.dynamicproxy.sample;

import com.mae.java.dynamicproxy.IInterceptor;
import com.mae.java.dynamicproxy.IInvocation;

import java.io.Serializable;

public class SuffixInterceptor implements IInterceptor {

    private static final long serialVersionUID = 2793429738409345L;

    private final String suffix;

    public SuffixInterceptor(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Object intercept(IInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        if (result instanceof String) {
            result = ((String) result) + suffix;
        }
        return result;
    }
}
