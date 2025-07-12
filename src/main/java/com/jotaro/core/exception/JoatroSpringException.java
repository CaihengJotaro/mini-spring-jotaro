package com.jotaro.core.exception;

import com.jotaro.core.code.JotaroErrorCode;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description 自定义异常
 */

public class JoatroSpringException extends RuntimeException {

    private String code;

    public JoatroSpringException(String message) {
        super(message);
    }

    public JoatroSpringException(JotaroErrorCode errorCode, String formatMsg) {
        String msg = String.format(errorCode.getMsg(),formatMsg);
        super(msg);
        this.code = errorCode.getCode();
    }
}
