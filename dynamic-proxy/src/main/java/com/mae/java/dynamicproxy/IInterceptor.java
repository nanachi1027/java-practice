package com.mae.java.dynamicproxy;

import java.io.Serializable;

public interface IInterceptor extends Serializable {
    Object intercept(IInvocation invocation) throws Throwable;
}
