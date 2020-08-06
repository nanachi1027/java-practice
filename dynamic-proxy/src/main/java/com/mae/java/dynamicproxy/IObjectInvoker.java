package com.mae.java.dynamicproxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 作用等价于 JDK 中的 InvocationHandler 其中定义了 invoke 方法.
 * 它的实现类通过 implement 这个接口 + 传入调用方法的实体类将调用对象和调用方法绑定在一起.
 */
public interface IObjectInvoker extends Serializable {
    Object invoke(Object proxy, Method method, Object ... arguments) throws Throwable;
}
