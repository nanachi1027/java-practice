package com.mae.java.dynamicproxy;


import java.util.Arrays;
import java.util.ServiceLoader;

public class ProxyBuilderDefaultImpl implements IProxyBuilder {

    public static final ProxyBuilderDefaultImpl INSTANCE = new ProxyBuilderDefaultImpl();

    private static final ServiceLoader<IProxyBuilder> SERVICES = ServiceLoader.load(IProxyBuilder.class);

    public boolean supportProxy(Class<?>... proxyClasses) {
        for (IProxyBuilder proxyBuilder : SERVICES) {
            if (proxyBuilder.supportProxy(proxyClasses)) {
                return true;
            }
        }
        return false;
    }

    public <T> T createDelegatorProxy(IObjectProvider<?> delegatorProvider, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createDelegatorProxy(delegatorProvider, proxyClasses);
        return result;
    }

    public <T> T createDelegatorProxy(ClassLoader classLoader, IObjectProvider<?> delegateProvider, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createDelegatorProxy(classLoader, delegateProvider, proxyClasses);
        return result;
    }

    public <T> T createInterceptorProxy(Object target, IInterceptor interceptor, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createInterceptorProxy(target, interceptor, proxyClasses);
        return result;
    }

    public <T> T createInterceptorProxy(ClassLoader classLoader, Object target, IInterceptor interceptor, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createInterceptorProxy(classLoader, target, interceptor, proxyClasses);
        return result;
    }

    public <T> T createInvokerProxy(IObjectInvoker invoker, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createInvokerProxy(invoker, proxyClasses);
        return result;
    }

    public <T> T createInvokerProxy(ClassLoader classLoader, IObjectInvoker invoker, Class<?>... proxyClasses) {
        T result = (T) getProxyBuilder(proxyClasses).createInvokerProxy(classLoader, invoker, proxyClasses);
        return result;
    }

    private IProxyBuilder getProxyBuilder(Class<?>... proxyClasses) {
        for (IProxyBuilder proxyBuilder : SERVICES) {
            if (proxyBuilder.supportProxy(proxyClasses)) {
                return proxyBuilder;
            }
        }
        throw new IllegalArgumentException(Arrays.toString(proxyClasses) + " couldn't proxy");
    }
}
