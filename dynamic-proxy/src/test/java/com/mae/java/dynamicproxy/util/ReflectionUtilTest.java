package com.mae.java.dynamicproxy.util;

import com.mae.java.dynamicproxy.MethodSignatureHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

class SubClass extends BaseClass {

}

class BaseClass extends ParentClass {

}

class ParentClass {
    public String hello(String name) {
        return "hello " + name;
    }
}

public class ReflectionUtilTest {

    private MethodSignatureHandler methodSignatureHandler = null;

    @Test
    public void getMethod() {
        Method m = ReflectionUtil.getMethod(ParentClass.class, "hello", new Class<?>[]{String.class});
        methodSignatureHandler = new MethodSignatureHandler(m);
        Assert.assertEquals("hello(Ljava/lang/String;)", methodSignatureHandler.toString());
        String methodName = MethodSignatureHandler.getMethodName(ParentClass.class.getName(), "hello", new Class<?>[]{String.class});
        System.out.println(methodName);
    }

    @Test
    public void hasParentClass() {
        Class<?> parentCls = ParentClass.class;
        Class<?> baseCls = BaseClass.class;
        Assert.assertFalse(ReflectionUtil.hasParentClass(parentCls));
        Assert.assertTrue(ReflectionUtil.hasParentClass(baseCls));
    }
}