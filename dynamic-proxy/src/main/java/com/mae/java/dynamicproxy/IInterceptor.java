package com.mae.java.dynamicproxy;

import java.io.Serializable;

/**
 *  定义了拦截器拦截的方法.
 *  其实现类
 */
public interface IInterceptor extends Serializable {
    Object intercept(IInvocation invocation) throws Throwable;
}
