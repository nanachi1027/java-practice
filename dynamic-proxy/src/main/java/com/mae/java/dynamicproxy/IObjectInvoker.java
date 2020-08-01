package com.mae.java.dynamicproxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface IObjectInvoker extends Serializable {
    Object invoke(Object proxy, Method method, Object ... arguments) throws Throwable;
}
