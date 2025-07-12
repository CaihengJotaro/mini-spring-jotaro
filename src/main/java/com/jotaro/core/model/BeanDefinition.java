package com.jotaro.core.model;

import com.jotaro.core.model.args.ArgumentValues;
import com.jotaro.core.model.props.PropertyValues;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description beanDefinition
 */


@Data
@AllArgsConstructor
public class BeanDefinition {

    /**
     * 单例枚举
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 多例枚举
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 懒加载
     */
    private boolean lazyInit = false;

    /**
     * 依赖array
     */
    private String[] dependsOn;

    /**
     * 构造参数(construct需要)
     */
    private ArgumentValues constructorArgumentValues;

    /**
     * 属性参数(get/set需求)
     */
    private PropertyValues propertyValues;

    /**
     * 初始化
     */
    private String initMethodName;

    /**
     * class记录
     */
    private volatile Object beanClass;

    /**
     * 一般beanName
     */
    private String beanId;

    /**
     * bean的全类名
     */
    private String className;
    
}
