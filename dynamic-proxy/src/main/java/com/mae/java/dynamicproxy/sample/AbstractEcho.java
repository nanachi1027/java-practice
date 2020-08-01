package com.mae.java.dynamicproxy.sample;

import java.io.Serializable;

public abstract class AbstractEcho implements Echo, Serializable {
    private static final long serialVersionUID = 2728394503982384905L;

    @Override
    public String echoBack(String message) {
        return message;
    }
}
