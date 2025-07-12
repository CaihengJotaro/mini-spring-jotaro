package com.jotaro.regitsry;

import com.jotaro.core.model.BeanDefinition;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description beanDefinition注册器
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册beanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 移除beanDefinition
     * @param beanName
     */
    void removeBeanDefinition(String beanName);

    /**
     * 通过beanName获得beanDefinition
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 校验是否包含目标beanName的beanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);


}
