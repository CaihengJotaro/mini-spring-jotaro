package com.jotaro.core.model.props;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description init注入
 */
@Data
@AllArgsConstructor
public class PropertyValue {
    private String type;
    private String name;
    private Object value;
    private boolean isReference;
    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}