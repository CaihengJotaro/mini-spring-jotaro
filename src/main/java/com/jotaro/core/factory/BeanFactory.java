package com.jotaro.core.factory;

import com.jotaro.minibean.BeanDefinition;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description bean工厂
 */

public interface BeanFactory {
    // 获得bean
    Object getBean(String name);

    // 注册beanDefinition
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
