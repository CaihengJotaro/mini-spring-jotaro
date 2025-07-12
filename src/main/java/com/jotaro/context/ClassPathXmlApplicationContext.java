package com.jotaro.context;

import com.jotaro.core.factory.BeanFactory;
import com.jotaro.core.factory.impl.JotaroBeanFactoryImpl;
import com.jotaro.core.resource.Resource;
import com.jotaro.core.resource.XmlBeanDefinitionReader;
import com.jotaro.core.resource.impl.ClassPathXmlResource;

import com.jotaro.core.model.BeanDefinition;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description xml文件解析器
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    private BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        JotaroBeanFactoryImpl beanFactory = new JotaroBeanFactoryImpl();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;

    }

    @Override
    public Object getBean(String name) {
        return this.beanFactory.getBean(name);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }
}
