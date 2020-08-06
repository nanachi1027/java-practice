package com.mae.java.dynamicproxy.demo.reflects;

import com.google.common.base.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import javax.swing.text.html.Option;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Mae
 * @Date: 2020/8/5 10:29 下午
 */

class BaseClass {
    private int a;
    protected int b;
    public int c;

    private String hi(String name) {
        return "hi " + name;
    }

    protected String hello(String name) {
        return "hello " + name;
    }

    public String hay(String name) {
        return "hay " + name;
    }
}

class CurClass extends BaseClass {

    public CurClass() {}
    // short for good morning
    public String gdmn(String name) {
        return "good morning " + name;
    }

    protected String gdaftn(String name) {
        return "good afternoon " + name;
    }

    private String gdnt(String name, Integer value) {
        return "good night * " + value + " times to " + name;
    }
}


public class Test {
    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("com.mae.java.dynamicproxy.demo.reflects.CurClass");

            // get super class
            Class<?> parentCls = cls.getSuperclass();
            Assert.assertEquals(parentCls, BaseClass.class);

            // get all declared methods
            Method [] methods = cls.getDeclaredMethods();
            Set<String> declaredMSet = new HashSet<String>();
            for (Method method : methods) {
                declaredMSet.add(method.getName());
            }

            Assert.assertTrue(declaredMSet.contains("gdmn"));
            Assert.assertTrue(declaredMSet.contains("gdaftn"));
            Assert.assertTrue(declaredMSet.contains("gdnt"));

            Method [] allMethods = cls.getMethods();
            for (Method m : allMethods) {
                System.out.println(m.getName());
            }

            // === reflect ===
            String className = "com.mae.java.dynamicproxy.demo.reflects.CurClass";
            try {
                // here we use default constructor to create new instance instead of directly
                // call newInstance(which is already deprecated in JDK9);
                Class<?> clsAInstance = Class.forName(className);
                Optional<? extends Constructor<?>> consOpt = getDefaultConstructor(clsAInstance);
                Object obj = null;
                if (consOpt.isPresent()) {
                    Constructor<?> defaultCon = consOpt.get();
                    obj = defaultCon.newInstance();
                }

                Method gdmnM = clsAInstance.getMethod("gdmn", String.class);
                String ret = (String) gdmnM.invoke(obj, " niania");
                System.out.println("ret=" + ret);

                Class<?> [] parameterTypes = new Class<?> [] {String.class, Integer.class};
                // if this is a private method, we need use getDeclaredMethod
                // getMethod can't find private method
                Method gdnt = clsAInstance.getDeclaredMethod("gdnt", parameterTypes);
                gdnt.setAccessible(true);
                ret = (String) gdnt.invoke(obj, new Object [] {" xiaoru", 5});
                System.out.println(ret);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static Optional<? extends Constructor<?>> getDefaultConstructor(Class<?> clsAInstance) {
        Constructor<?>[] conArr = clsAInstance.getConstructors();
        if (!ArrayUtils.isNotEmpty(conArr)) {
            return Optional.absent();
        }

        // here we get the default constructor which input params is empty/none
        for (Constructor<?> c : conArr) {
            if (!ArrayUtils.isNotEmpty(c.getParameterTypes())) {
                return Optional.of(c);
            }
        }
        return Optional.absent();
    }
}
