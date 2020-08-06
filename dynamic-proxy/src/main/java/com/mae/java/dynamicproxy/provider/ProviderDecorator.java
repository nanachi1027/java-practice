package com.mae.java.dynamicproxy.provider;

import com.mae.java.dynamicproxy.IObjectProvider;

/**
 * @Author: Mae
 * @Date: 2020/8/2 6:11 下午
 *
 * ProviderDecorator obeys Decorate design pattern.
 * It maintains a interface reference and supports passing multiple
 * IObjectProvider's implementation passing in implementation instance
 * can call getObject to create different T instances.
 */
public class ProviderDecorator<T> implements IObjectProvider<T> {

    private static final long serialVersionUID = 3456745678456789L;

    private IObjectProvider<? extends T> inner;

    public ProviderDecorator(IObjectProvider<? extends T> inner) {
        this.inner = inner;
    }

    protected IObjectProvider<? extends T> getInner() {
        return inner;
    }

    public void setInner(IObjectProvider<? extends T> inner) {
        this.inner = inner;
    }

    @Override
    public T getObject() {
        return inner.getObject();
    }
}
