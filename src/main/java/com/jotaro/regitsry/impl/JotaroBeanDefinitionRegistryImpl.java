package com.jotaro.regitsry.impl;

import com.jotaro.core.model.BeanDefinition;
import com.jotaro.regitsry.BeanDefinitionRegistry;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description 自定义注册器实现类
 */
public class JotaroBeanDefinitionRegistryImpl implements BeanDefinitionRegistry {

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }
}
