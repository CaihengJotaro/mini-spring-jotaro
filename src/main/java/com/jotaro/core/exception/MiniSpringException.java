package com.jotaro.core.exception;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description 自定义异常
 */

public class MiniSpringException extends RuntimeException {
    public MiniSpringException(String message) {
        super(message);
    }
}
