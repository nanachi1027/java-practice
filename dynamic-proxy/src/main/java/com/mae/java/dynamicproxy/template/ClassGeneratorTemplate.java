package com.mae.java.dynamicproxy.template;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mae.java.dynamicproxy.MethodSignatureHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Template class of IClassGenerator
 * implements part of the common methods
 */
public abstract class ClassGeneratorTemplate implements IClassGenerator {

    /**
     * extract multi to be implemented methods from given proxy classes
     *
     * @param proxyClasses a set of proxy classes
     * @return method to be implemented
     *
     * 1. create a local cache to hold signature and method
     * 2. also need a set to hold all the method signature instances
     * 3. traverse classes,
     * 4.     traverse each proxy's methods and create method signature based on the method
     * 5.     check weather the method is final decorated
     *        yes: add to set
     *        no: put to map
     * 6. collect all the method to collection
     * 7. traverse map , remove the final decorated methods by elements in the set
     * 8. return oter remained methods.
     */
    public static Method[] getImplementationMethods(Class<?> [] proxyClasses) {
        Map<MethodSignatureHandler, Method> signatureMethodMap = Maps.newHashMap();
        final Set<MethodSignatureHandler> finalMethodSignatureSet = Sets.newHashSet();

        for (Class<?> proxyCls : proxyClasses) {
            Method [] methods = proxyCls.getDeclaredMethods();
            for (Method method : methods) {
                MethodSignatureHandler methodSignature = new MethodSignatureHandler(method);
                if (Modifier.isFinal(method.getModifiers())) {
                    finalMethodSignatureSet.add(methodSignature);
                } else {
                    signatureMethodMap.put(methodSignature, method);
                }
            }
        }

        Collection<Method> methodCollection = signatureMethodMap.values();
        for (MethodSignatureHandler methodSignature : finalMethodSignatureSet) {
            methodCollection.remove(signatureMethodMap.get(methodSignature));
        }

        return methodCollection.toArray(new Method[methodCollection.size()]);
    }
}
