/**
 * @Author: Mae
 * @Date: 2020/8/2 7:16 下午
 */
package com.mae.java.dynamicproxy.impl;
/***
 * 动态代理执行步骤:
 * 1. 获取 RealSubject 上所有接口列表; 只有 RealSubject 上有接口, 它才能是可 proxy 的.
 * 2. 确定要生成的代理类的类名, 默认就是 com.sun.proxy.$PoxyXXXXX.
 * 3. 根据需要实现的接口信息, 在代码中动态的创建该 Proxy 类的字节码.
 * 4. 能将对应的字节码转换为 Class 对象.
 * 5. 创建 InvocationHandler 实例, 用来处理 Proxy 所有的方法调用.
 * 6. Proxy 的 class 对象以创建的 handler 对象为参数，实例化一个 Proxy 对象实例.
 *
 * 动态代理的实现是基于实现接口的方式, 让 RealSubject 和 Proxy 具有相同的功能, 这个是在 JDK 中.
 * 在 Cglib 中是通过让 Proxy 继承 RealSubject 的方式来实现.
 *
 * Java 的动态代理中, 有 2 个类十分重要, 一个是 InvocationHandler, 另一个是 Proxy 类;
 * > InvocationHandler
 * Object invoke(Object proxy, Method method, Object [] args) throws Throwable;
 *
 * > Proxy 这个类的作用就是创建出一个 Proxy 实例对象出来,
 * Proxy#newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h);
 *   loader: ClassLoader 用来指定哪个 loader 来加载代理对象；
 *   interfaces: 接口数组,
 *
 */