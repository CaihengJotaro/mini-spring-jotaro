package com.jotaro.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description 错误码
 */
@Getter
@AllArgsConstructor
public enum JotaroErrorCode {


    BEAN_DEFINITION_NOT_REGISTER("-100","bean: {} 未被注册")


    ;


    /**
     * 错误码
     */
    private String code;


    /**
     * 错误信息
     */
    private String msg;




}
