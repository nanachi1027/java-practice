package com.mae.java.dynamicproxy.template;

import com.mae.java.dynamicproxy.IInterceptor;
import com.mae.java.dynamicproxy.IObjectInvoker;
import com.mae.java.dynamicproxy.IObjectProvider;
import com.mae.java.dynamicproxy.IProxyBuilder;

public abstract class ProxyBuilderTemplate implements IProxyBuilder {

    /**
     * Can those proxy classes be proxy classes(should be interface instead of class)
     *
     * @param proxyClasses a set of classes
     * @return true all can be proxy classes, false one or more can not be proxy class(es).
     */
    @Override
    public boolean supportProxy(Class<?>... proxyClasses) {
        for (Class<?> proxyCls : proxyClasses) {
            if (!proxyCls.isInterface()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T> T createDelegatorProxy(IObjectProvider<?> delegatorProvider, Class<?>... proxyClasses) {
        return createDelegatorProxy(Thread.currentThread().getContextClassLoader(),
                delegatorProvider, proxyClasses);
    }

    @Override
    public <T> T createInterceptorProxy(Object target, IInterceptor interceptor, Class<?>... proxyClasses) {
        return createInterceptorProxy(Thread.currentThread().getContextClassLoader(),
                target, interceptor, proxyClasses);
    }

    @Override
    public <T> T createInvokerProxy(IObjectInvoker invoker, Class<?>... proxyClasses) {
        return createInvokerProxy(Thread.currentThread().getContextClassLoader(), invoker, proxyClasses);
    }
}
