package com.mae.java.dynamicproxy.util;

public interface Entryable<K, V> {
    /**
     * return the key of the entry
     *
     * @return key
     */
    K getKey();

    /**
     * return the value of the entry
     *
     * @return value
     */
    V getValue();
}
