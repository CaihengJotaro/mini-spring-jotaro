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


    BEAN_DEFINITION_NOT_REGISTER("-100","bean: %s 未被注册"),

    BEAN_INITIALIZATION_ERROR("-101","bean: %s, 初始化失败"),

    BEAN_CREATE_ERROR("-102","bean: %s, 有参构造失败, 参数集合: %s, 参数值: %s"),

    BEAN_INVOKE_SETTER_FUNCTION_ERROR("-103","bean: %s, 调用set方法注入异常, field: %s, value: %s"),


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
