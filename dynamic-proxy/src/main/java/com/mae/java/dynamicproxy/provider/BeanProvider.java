package com.mae.java.dynamicproxy.provider;

import com.mae.java.dynamicproxy.IObjectProvider;
import com.mae.java.dynamicproxy.util.ReflectionUtil;

import java.io.Serializable;

/**
 * BeanProvider is a generic class which implements ObjectProvider<T> and Serializable interfaces.
 * It is responsible for create new instance by its `getObject` function.
 * And all the class created by BeanProvider as a java bean should be the subclass of T.
 *
 * I have a question: why some of the classes implements Serializable ?
 */
public class BeanProvider<T> implements IObjectProvider<T>, Serializable {

    private static final long serialVersionUID = 93283423823084298L;

    private final Class<? extends T> objClazz;

    public BeanProvider(Class<? extends  T> objClazz) {
        this.objClazz = objClazz;
    }

    @Override
    public T getObject() {
        return ReflectionUtil.newInstance(objClazz);
    }
}
