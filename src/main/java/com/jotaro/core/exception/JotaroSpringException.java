package com.jotaro.core.exception;

import com.jotaro.core.code.JotaroErrorCode;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description 自定义异常
 */

public class JotaroSpringException extends RuntimeException {

    private String code;

    private String message;

    public JotaroSpringException (String message) {
        super(message);
    }

    public JotaroSpringException (JotaroErrorCode errorCode, Object... formatMsg) {
        String msg = String.format(errorCode.getMsg(),formatMsg);
        this.message = msg;
        this.code = errorCode.getCode();
    }
}
