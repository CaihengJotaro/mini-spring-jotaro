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
     * 懒加载标记
     */
    private boolean lazyInit = false;

    /**
     * dependsOn 属性记录 Bean 之间的依赖关系
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
     * 初始化: 当一个 Bean 构造好并实例化之后是否要让框架调用初始化方法
     */
    private String initMethodName;

    /**
     * class记录
     */
    private volatile Object beanClass;

    /**
     * 默认单例模式
     */
    private String scope = SCOPE_SINGLETON;

    /**
     * 一般beanName
     */
    private String beanId;

    /**
     * bean的全类名
     */
    private String className;

    public BeanDefinition(String beanId, String className) {
        this.beanId = beanId;
        this.className = className;
    }

    /**
     * 是否是单例模式
     * @return
     */
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    /**
     * 是否是多例模式
     * @return
     */
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }






}
