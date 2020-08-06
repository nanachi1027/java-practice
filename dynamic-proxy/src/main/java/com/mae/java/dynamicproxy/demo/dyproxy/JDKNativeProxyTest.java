package com.mae.java.dynamicproxy.demo.dyproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Mae
 * @Date: 2020/8/6 12:50 上午
 */

interface IService {
    String hello(String name, Integer times);
}

class ServiceImpl implements IService {
    @Override
    public String hello(String name, Integer times) {
        return "Hello " + name + " * " + times;
    }
}

class MyInvocationHandler implements InvocationHandler {

    private IService service;

    public MyInvocationHandler(IService service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("You can add other logic to intercept this method before");
        Object ret = method.invoke(service, objects);
        System.out.println("You can add other logic to intercept this method after");
        return ret;
    }
}


public class JDKNativeProxyTest {
    public static void main(String[] args) {
        IService service = new ServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(service);

        ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
        // we use Proxy.newInstance to create the instance which implements the specified interface
        // param1: class loader use current thread's is fine
        // param2: the interface array which includes the object implemented interface(type is Class<?> [] )
        // param3: the invocation instance which implements te InvocationHandler and implement's its invoke method
        IService serviceHandler =
                (IService) Proxy.newProxyInstance(clsLoader, new Class<?>[]{IService.class}, myInvocationHandler);
        System.out.println(serviceHandler.hello("xiaoru ", 6));
    }
}