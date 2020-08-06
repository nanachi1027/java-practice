package com.mae.java.dynamicproxy.provider;

import com.mae.java.dynamicproxy.IObjectProvider;

import java.io.Serializable;

/**
 * ConstantProvider implements IObjectProvider and Serializable.
 */
public class ConstantProvider<T> implements IObjectProvider<T>, Serializable {

    private static final long serialVersionUID = 27839427839723894L;

    private final T constant;

    public ConstantProvider(T constant) {
        this.constant = constant;
    }

    @Override
    public T getObject() {
        return constant;
    }
}
