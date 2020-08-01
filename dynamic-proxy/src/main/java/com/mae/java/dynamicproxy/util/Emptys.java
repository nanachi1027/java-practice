package com.mae.java.dynamicproxy.util;

import java.io.Serializable;

public class Emptys {

    // empty byte arrays
    public static final Byte [] EMPTY_BYTE_OBJECT_ARR = new Byte[0];
    public static final byte [] EMPTY_BYTE_ARR = new byte[0];

    // empty short array
    public static final Short [] EMPTY_SHORT_OBJECT_ARR = new Short[0];
    public static final short [] EMPTY_SHORT_ARR = new short[0];

    // empty int array
    public static final Integer [] EMPTY_INTEGER_OBJECT_ARR = new Integer[0];
    public static final int [] EMPTY_INTEGER_ARR = new int[0];

    // empty long array
    public static final Long [] EMPTY_LONG_OBJECT_ARR = new Long[0];
    public static final long [] EMPTY_LONG_ARR = new long[0];

    // empty float array
    public static final Float [] EMPTY_FLOAT_OBJECT_ARR = new Float[0];
    public static final float [] EMPTY_FLOAT_ARR = new float[0];

    // empty double array
    public static final Double [] EMPTY_DOUBLE_OBJECT_ARR = new Double[0];
    public static final double [] EMPTY_DOUBLE_ARR = new double[0];

    // empty char array
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARR = new Character[0];
    public static final char[] EMPTY_CHARACTER_ARR = new char[0];

    // empty boolean array
    public static final Boolean [] EMPTY_BOOLEAN_OBJECT_ARR = new Boolean[0];
    public static final boolean [] EMPTY_BOOLEAN_ARR = new boolean[0];

    // empty Object array
    public static final Object [] EMPTY_OBJECT_ARR = new Object[0];


    // empty Class array
    public static final Class<?> [] EMPTY_CLASS_ARR = new Class<?>[0];

    // empty String array
    public static final String [] EMPTY_STRING_ARR = new String[0];

    // === empty primitive

    public static final Short SHORT_ZERO = (short) 0;
    public static final Integer INTEGER_ZERO= 0;
    public static final Long LONG_ZERO = 0L;
    public static final Float FLOAT_ZERO = 0f;
    public static final Double DOUBLE_ZERO = 0.0;
    public static final Byte BYTE_ZERO = (byte) 0;
    public static final Boolean BOOL_ZERO = Boolean.FALSE;
    public static final Character CHAR_ZERO = '\0';

    // empty String
    public static final String EMPTY_STRING = "";

    // empty null place holder
    private static final Object NULL_PLACEHOLDER = new NullPlaceHolder();

    private static final class NullPlaceHolder implements Serializable {

        private static final long serialVersionUUID = 456789023456788765L;

        @Override
        public String toString() {
            return "null";
        }

        private Object readResolve() {
            return NULL_PLACEHOLDER;
        }
    }
}
