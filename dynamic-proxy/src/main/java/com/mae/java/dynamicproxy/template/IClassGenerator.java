package com.mae.java.dynamicproxy.template;

/**
 * ClassGenerator defines how to generate proxy class based
 * on given class loader and a series of proxy classes
 */
public interface IClassGenerator {
    Class<?> generateProxyClass(ClassLoader classLoader, Class<?> ... proxyClasses);
}
