package com.jotaro.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description jdk部分基本类型
 */
@Getter
@AllArgsConstructor
public enum BasicType {

    STRING("String", String.class),

    INTEGER("Integer", Integer.class),

    DOUBLE("Double", Double.class),

    BOOLEAN("Boolean", Boolean.class),

    LONG("Long", Long.class),

    Int("int",int.class),

    Double("double",double.class),

    Long("long",long.class),

    ;


    private String name;

    private Class<?> clazz;

    public static BasicType fromString(String name) {
        for (BasicType basicType : BasicType.values()) {
            if (basicType.name.equals(name)) {
                return basicType;

            }
        }
        return null;
    }

    public Object parseOfValue(Object value) {
        String stringValue = value.toString();
        if (String.class.equals(clazz)) {
            return value;
        }else if (Integer.class.equals(clazz)) {
            return Integer.valueOf(stringValue);
        }else if (Double.class.equals(clazz)) {
            return Double.valueOf(stringValue);
        }else if (Boolean.class.equals(clazz)) {
            return Boolean.valueOf(stringValue);
        }else if (Long.class.equals(clazz)) {
            return Long.valueOf(stringValue);
        }else if (int.class.equals(clazz)) {
            return Integer.valueOf(stringValue);
        }else if (double.class.equals(clazz)) {
            return Double.valueOf(stringValue);
        }else if (boolean.class.equals(clazz)) {
            return Boolean.valueOf(stringValue);
        }else if (long.class.equals(clazz)) {
            return Long.valueOf(stringValue);
        }
        return value;
    }

}
