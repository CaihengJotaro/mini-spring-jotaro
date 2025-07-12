package com.jotaro.core.factory;

import com.jotaro.core.model.BeanDefinition;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description bean工厂
 */

public interface BeanFactory {
    /**
     * 通过beanName获得bean实例
     * @param name
     * @return
     */
    Object getBean(String name);

    /**
     * 注册beanDefinition
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

    /**
     * 依据beanName判断是否存在该bean
     * @param name
     * @return
     */
    boolean containsBean(String name);

    /**
     * 判断bean是否是单例
     * @param name
     * @return
     */
    boolean isSingleton(String name);

    /**
     * 判断bean是否是多例
     * @param name
     * @return
     */
    boolean isPrototype(String name);


    /**
     * 获取bean的class类型
     * @param name
     * @return
     */
    Class<?> getType(String name);
}
