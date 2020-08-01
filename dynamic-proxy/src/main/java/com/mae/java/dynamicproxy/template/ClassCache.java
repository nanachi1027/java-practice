package com.mae.java.dynamicproxy.template;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Cache for classloader and the series of classes this loader loads.
 *
 * ClassCache contains multiple classes load by different class loaders.
 *
 * In case of one class loader loads duplicated classes, we use Set to keep classes.
 * ClassCache's local cache is created inside the java's heap monitored by GC
 * in case of memory leak, we use WeakReverence wraps all the classes load by the classloader.
 */

public class ClassCache {
    private final Map<ClassLoader, Map<Set<Class<?>>, WeakReference<Class<?>>>> loaderToClassCache = Maps.newHashMap();

    private final IClassGenerator proxyClassGenerator;

    public ClassCache(IClassGenerator proxyClassGenerator) {
        this.proxyClassGenerator = proxyClassGenerator;
    }

    private Map<Set<Class<?>>, WeakReference<Class<?>>> getClassCache(ClassLoader clsLoader) {
        Map<Set<Class<?>>, WeakReference<Class<?>>> cache = loaderToClassCache.get(clsLoader);
        if (cache == null) {
            cache = Maps.newHashMap();
            loaderToClassCache.put(clsLoader, cache);
        }
        return cache;
    }

    /**
     * Transfer class array to class set which gonna be key of the class loader cache
     * @param proxyClass array of classes
     * @return a set of classes
     */
    public Set<Class<?>> transferToClassCacheKey(Class<?> [] proxyClass) {
        return Sets.newHashSet(Arrays.asList(proxyClass));
    }

    /**
     * Method to get proxy class from cache by given classLoader and a set of proxy classes
     * @param classLoader classloader
     * @param proxyClasses an array of proxy class
     *
     * 1. get Map<Set<Class<?>, WeakReference<Class<?>> from classloader cache
     * 2. transfer array of proxy class to set
     * 3. use set as map key to get Class<?>
     *
     * 4. check whether the class is null, if null
     *      call proxy class creater to create one and add to map
     * 5. else not null
     *      if reference's value not null return
     *      else the same way to create a new one
     */
    public synchronized Class<?> getProxyClass(ClassLoader classLoader, Class<?>[] proxyClasses) {
        Map<Set<Class<?>>, WeakReference<Class<?>>> classCache = loaderToClassCache.get(classLoader);
        Set<Class<?>> cacheKey = transferToClassCacheKey(proxyClasses);
        Class<?> proxyClass;
        Reference<Class<?>> proxyClassReference = classCache.get(cacheKey);

        if (proxyClassReference == null) {
            proxyClass = proxyClassGenerator.generateProxyClass(classLoader, proxyClasses);
            classCache.put(cacheKey, new WeakReference<Class<?>>(proxyClass));
        } else {
            synchronized (proxyClassReference) {
                if (proxyClassReference.get() != null) {
                    proxyClass = proxyClassReference.get();
                }
                proxyClass = proxyClassGenerator.generateProxyClass(classLoader, proxyClasses);
                classCache.put(cacheKey, new WeakReference<Class<?>>(proxyClass));
            }
        }
        return proxyClass;
    }
}
