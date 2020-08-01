package com.mae.java.dynamicproxy.util;

import java.io.Serializable;

public abstract class Entity<T extends Entity<T>> implements Serializable {

    private static final long serialVersionUID = -2345678345678934567L;

    public Entity() {}


    /**
     * method to check whether two object are equal to each other
     * @param obj instance needs to check whether equals to current instance
     * @return true equals, else false
     *
     * 1. address compare
     * 2. address not match, check obj not null and the class are the same as current class
     *    transfer obj from Object to current class type continue compare
     * 3. not equal
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            // the same address
            return true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            T objInstance = (T) obj;
            return this.isEquals(objInstance);
        } else {
            return false;
        }
    }

    protected abstract boolean isEquals(T val);

    protected abstract Object hashKey();

    public int hashCode() {
        if (this.hashKey() == null) {
            return super.hashCode();
        } else {
            byte result = 1;
            int result1 = 31 * result + this.hashKey().hashCode();
            return result1;
        }
     }
}
