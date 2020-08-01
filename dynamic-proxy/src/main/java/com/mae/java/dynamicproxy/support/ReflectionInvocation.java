package com.mae.java.dynamicproxy.support;

import com.mae.java.dynamicproxy.IInvocation;
import com.mae.java.dynamicproxy.util.Emptys;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionInvocation implements IInvocation {

    private final Object target;
    private final Object proxy;
    private final Method invokeMethod;
    private final Object [] argList;

    public ReflectionInvocation(final Object target, final Object proxy, final Method invokeMethod, final Object [] argList) {
        this.target = target;
        this.proxy = proxy;
        this.invokeMethod = invokeMethod;
        this.argList = ObjectUtils.defaultIfNull(ArrayUtils.clone(argList), Emptys.EMPTY_OBJECT_ARR);
    }

    @Override
    public Object[] getArgs() {
        return argList;
    }

    @Override
    public Method getMethod() {
        return invokeMethod;
    }

    @Override
    public Object getProxy() {
        return proxy;
    }

    @Override
    public Object proceed() throws Throwable {
        try {
            return invokeMethod.invoke(target, argList);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
