package com.mae.java.dynamicproxy;

/**
 * Define methods to creating multiple proxies like delegator, interceptor
 * and invoker.
 */
public interface IProxyBuilder {

    // are all the classes support proxy?
    boolean supportProxy(Class<?>... proxyClasses);

    // build proxy works as delegator
    <T> T createDelegatorProxy(IObjectProvider<?> delegatorProvider, Class<?>... proxyClasses);
    <T> T createDelegatorProxy(ClassLoader classLoader, IObjectProvider<?> delegateProvider, Class<?>... proxyClasses);

    // build proxy works as interceptor
    <T> T createInterceptorProxy(Object target, IInterceptor interceptor, Class<?> ... proxyClasses);
    <T> T createInterceptorProxy(ClassLoader classLoader, Object target, IInterceptor interceptor,
                                 Class<?> ... proxyClasses);

    // create invoker proxy
    <T> T createInvokerProxy(IObjectInvoker invoker, Class<?> ... proxyClasses);
    <T> T createInvokerProxy(ClassLoader classLoader, IObjectInvoker invoker, Class<?> ... proxyClasses);
}
