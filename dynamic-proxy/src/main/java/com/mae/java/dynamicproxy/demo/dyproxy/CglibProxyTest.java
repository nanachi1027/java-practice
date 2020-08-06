package com.mae.java.dynamicproxy.demo.dyproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Mae
 * @Date: 2020/8/6 1:07 上午
 */

interface CglibService {
    String sayHello(String name, int counts);
}

class CglibServiceImpl implements CglibService {

    private long id;

    public CglibServiceImpl() {
        id = -1;
    }

    public CglibServiceImpl(long id) {
        this.id = id;
    }

    @Override
    public String sayHello(String name, int counts) {
        return "ID:" + id + " Hello to " + name + " * " + counts;
    }
}

class BaseClass {
    private long id;

    public BaseClass() {
    }

    public BaseClass(long id) {
        this.id = id;
    }

    public String hello(String name, int times) {
        return String.format("ID:%s says hello to %s %d times", id, name, times);
    }
}


class BaseClassInterceptor extends BaseClass implements MethodInterceptor {

    /**
     * 1. Object instance is the BaseClass's proxy class which is generated during runtime ;
     * 2. method is the method that this current interceptor wants to override ;
     * 3. methodProxy is the method proxy whose responsibility for invoke method upon proxy/interceptor instance
     * and pass corresponding method's params
     */
    @Override
    public Object intercept(Object instance, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String cglibProxyClsName = instance.getClass().getSuperclass().getCanonicalName();
        String invokeMethodName = method.getName();
        System.out.println(String.format("Cglib Proxy Class Name: %s, invoke superclass: %s 's, method: %s [before intercept]",
                instance.getClass().getCanonicalName(), cglibProxyClsName, invokeMethodName));
        // invoke super class's method
        Object ret = methodProxy.invokeSuper(instance, objects);
        // Proxy Class Name: com.mae.java.dynamicproxy.demo.dyproxy.BaseClass$$EnhancerByCGLIB$$84fc2e7c, invoke superclass: com.mae.java.dynamicproxy.demo.dyproxy.BaseClass 's, method: hello [before intercept]
        System.out.println(String.format("Cglib Proxy Class Name: %s, invoke superclass: %s 's, method: %s [after intercept]",
                instance.getClass().getCanonicalName(), cglibProxyClsName, invokeMethodName));
        return ret;
    }
}

// not accurate, the interceptor is like invocation handler in JDK proxy in some degreee...
class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object instance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        String className = instance.getClass().getSimpleName();
        String methodName = method.getName();
        System.out.println(String.format("[Before Call] %s class gonna call %s method", className, methodName));
        Object obj = methodProxy.invokeSuper(instance, args);
        System.out.println(String.format("[After Call] %s class gonna call %s method", className, methodName));
        return obj;
    }
}

public class CglibProxyTest {

    public static void main(String[] args) {
        // ====== via interfaces
        // 1. create a Enhancer
        Enhancer enhancer = new Enhancer();
        // 2. set super class this super class is the one implements the interface or proxy instances want to expand
        enhancer.setSuperclass(CglibServiceImpl.class);
        // 3. set our invoker/interceptor inside the enhander
        enhancer.setCallback(new MyMethodInterceptor());
        // 4. create instance and call corresponding method
        CglibServiceImpl cglibService = (CglibServiceImpl) enhancer.create();

        String name = "zumulv";
        int counts = 5000;
        System.out.println(cglibService.sayHello(name, counts));

        // ====== via subclass
        Enhancer enhancerHandler = new Enhancer();
        enhancerHandler.setSuperclass(BaseClass.class);
        enhancerHandler.setCallback(new BaseClassInterceptor());
        BaseClass baseClassInstance = (BaseClass) enhancerHandler.create();

        System.out.println(baseClassInstance.hello(name, counts));
    }
}
