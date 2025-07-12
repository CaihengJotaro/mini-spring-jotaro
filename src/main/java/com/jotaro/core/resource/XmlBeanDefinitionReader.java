package com.jotaro.core.resource;

import com.jotaro.core.factory.impl.JotaroBeanFactoryImpl;
import com.jotaro.core.model.BeanDefinition;
import org.dom4j.Element;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description xml的bean解析器
 */
public class XmlBeanDefinitionReader {
    /**
     * beanFactory实现类
     */
    private JotaroBeanFactoryImpl beanFactory;
    public XmlBeanDefinitionReader(final JotaroBeanFactoryImpl beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(final Resource resource) {
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("beanId");
            String className = element.attributeValue("className");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }


}
