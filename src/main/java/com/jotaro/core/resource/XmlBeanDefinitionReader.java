package com.jotaro.core.resource;

import com.jotaro.core.factory.BeanFactory;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description xml的bean解析器
 */
public class XmlBeanDefinitionReader {
    // bean工厂
    private BeanFactory beanFactory;
    public XmlBeanDefinitionReader(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


}
