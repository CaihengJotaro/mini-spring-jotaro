package com.jotaro.core.model.args;

import lombok.Data;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description bean的参数注入
 */
@Data
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }
    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

}