package com.mae.java.dynamicproxy;

import java.io.Serializable;

public interface IObjectProvider<T> extends Serializable {
    T getObject();
}
