package com.mae.java.dynamicproxy.util;

public class Pair<K, V> extends Entity<Pair<K, V>> implements Entryable<K, V> {

    private static final long serialVersionUID = -324345675678435L;

    protected K key;

    protected V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    protected boolean isEquals(Pair<K, V> val) {
        return this.key.equals(val.key) && this.value.equals(val.value);
    }

    @Override
    protected Object hashKey() {
        int result = this.key == null ? 0 : 31 * this.key.hashCode();
        result = this.value == null ? 0 : this.value.hashCode() + result;
        return Integer.valueOf(result);
    }
}
