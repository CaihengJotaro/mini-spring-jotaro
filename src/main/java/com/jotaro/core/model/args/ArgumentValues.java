package com.jotaro.core.model.args;

import java.util.*;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description
 */
public class ArgumentValues {
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();
    public ArgumentValues() {
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }
    public void addGenericArgumentValue(ArgumentValue newValue) {
        this.genericArgumentValues.add(newValue);
    }
    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() { return (this.genericArgumentValues.size()); }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public ArgumentValue getArgumentValue(int index) {
        return this.genericArgumentValues.get(index);
    }
}