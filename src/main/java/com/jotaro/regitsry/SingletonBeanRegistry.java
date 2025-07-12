package com.jotaro.regitsry;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description 单例bean注册接口
 */
public interface SingletonBeanRegistry {
    /**
     * 注册单例bean
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获得单例bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 是否存在目标bean
     * @param beanName
     * @return
     */
    boolean containsSingleton(String beanName);

    /**
     * 获得所有beanNames
     * @return
     */
    String[] getSingletonNames();
}