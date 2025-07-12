package com.jotaro.core.model.props;

import lombok.Data;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description init注入
 */
@Data
public class PropertyValue {
    private final String name;
    private final Object value;
    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

}