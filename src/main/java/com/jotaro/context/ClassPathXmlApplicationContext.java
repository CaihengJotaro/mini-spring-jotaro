package com.jotaro.context;

import com.jotaro.core.event.ApplicationEvent;
import com.jotaro.core.event.ApplicationEventPublisher;
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
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private JotaroBeanFactoryImpl beanFactory;

    public ClassPathXmlApplicationContext(String fileName,boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        JotaroBeanFactoryImpl beanFactory = new JotaroBeanFactoryImpl();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            this.beanFactory.refresh();
        }

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

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanFactory.getType(name);
    }

    public void publishEvent(ApplicationEvent event) {

    }
}
