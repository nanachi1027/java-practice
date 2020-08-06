package com.mae.java.dynamicproxy.provider;

import com.mae.java.dynamicproxy.IObjectProvider;

/**
 * @Author: Mae
 * @Date: 2020/8/2 6:09 下午
 */
public class SingletonProvider<T> extends ProviderDecorator<T> {

    private static final long serialVersionUID = 27839405273273849L;

    private T instance;

    public SingletonProvider(IObjectProvider<? extends  T> inner) {
        super(inner);
        instance = inner.getObject();
        setInner(null);
    }

    @Override
    public T getObject() {
        return instance;
    }
}
