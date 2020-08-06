/*
package com.mae.java.dynamicproxy.impl;

import com.mae.java.dynamicproxy.IInterceptor;
import com.mae.java.dynamicproxy.IObjectInvoker;
import com.mae.java.dynamicproxy.IObjectProvider;
import com.mae.java.dynamicproxy.support.ReflectionInvocation;
import com.mae.java.dynamicproxy.template.ProxyBuilderTemplate;
import com.mae.java.dynamicproxy.util.ProxyUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

*/
/**
 * @Author: Mae
 * @Date: 2020/8/2 6:25 下午
 *
 * Proxy implemented by JDK.
 *//*

public class ProxyBuilderJDKImpl extends ProxyBuilderTemplate {
    @Override
    public <T> T createDelegatorProxy(ClassLoader classLoader, IObjectProvider<?> delegateProvider,
                                      Class<?>... proxyClasses) {
        Proxy.newProxyInstance(classLoader, proxyClasses, new DelegatorIn)
    }

    @Override
    public <T> T createInterceptorProxy(ClassLoader classLoader, Object target, IInterceptor interceptor,
                                        Class<?>... proxyClasses) {
        return null;
    }

    @Override
    public <T> T createInvokerProxy(ClassLoader classLoader, IObjectInvoker invoker,
                                    Class<?>... proxyClasses) {
        return null;
    }

    */
/**
     * Class responsible for create invocation handler
     *
     *//*

    private static class DelegatorInvocationHandler extends AbstractInvocationHandler {

        private static final long serialVersionUID = 7283940233849342323L;

        private final IObjectProvider<?> objectProvider;

        public DelegatorInvocationHandler(IObjectProvider<?> objectProvider) {
            this.objectProvider = objectProvider;
        }

        @Override
        protected Object invokeHandler(Object proxy, Method method, Object[] params) {
            try {
                return method.invoke(objectProvider.getObject(), params);
            } catch (InvocationTargetException e) {
                return e.getTargetException();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class InterceptorInvocationHandler implements AbstractInvocationHandler {

        private static final long serialVersionUID = 3489509834504983L;

        private final Object target;
        private final IInterceptor methodInterceptor;

        public InterceptorInvocationHandler(Object target, IInterceptor methodInterceptor) {
            this.target = target;
            this.methodInterceptor = methodInterceptor;
        }

        @Override
        protected Object invokeHandler(Object proxy, Method method, Object[] params) {
            ReflectionInvocation invocation = new ReflectionInvocation(proxy, target, method, params);
            try {
                return methodInterceptor.intercept(invocation);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private abstract static class AbstractInvocationHandler implements InvocationHandler, Serializable {
        private static final long serialVersionUID = 72839402338493459L;

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            if (ProxyUtil.isHashCodeMethod(method)) {
                return Integer.valueOf(System.identityHashCode(proxy));
            }

            if (ProxyUtil.isEqualsMethod(method)) {
                return Boolean.valueOf(proxy == params[0]);
            }

            return invokeHandler(proxy, method, params);
        }

        protected abstract Object invokeHandler(Object proxy, Method method, Object[] params);
    }
}
*/
