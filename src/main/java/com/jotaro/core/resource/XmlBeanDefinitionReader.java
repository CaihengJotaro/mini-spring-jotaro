package com.jotaro.core.resource;

import com.google.common.collect.Lists;
import com.jotaro.core.factory.impl.JotaroBeanFactoryImpl;
import com.jotaro.core.model.BeanDefinition;
import com.jotaro.core.model.args.ArgumentValue;
import com.jotaro.core.model.args.ArgumentValues;
import com.jotaro.core.model.props.PropertyValue;
import com.jotaro.core.model.props.PropertyValues;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.List;

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

            // 处理属性
            List<Element> properties = element.elements("property");
            List<String> refs = Lists.newArrayList();
            PropertyValues pvs = new PropertyValues();
            for (Element property : properties) {
                String propertyType = property.attributeValue("type");
                String propertyName = property.attributeValue("name");
                String propertyValue = property.attributeValue("value");
                String propertyRef = property.attributeValue("ref");

                // 单独处理bean的注入
                boolean isRef = false;
                if(StringUtils.isBlank(propertyValue) && StringUtils.isNotBlank(propertyRef)){
                    isRef = true;
                    propertyValue = propertyRef;
                    refs.add(propertyRef);
                }
                pvs.addPropertyValue(new PropertyValue(propertyType,propertyName, propertyValue, isRef));
            }
            // 依赖预处理注入
            String[] refArray = refs.toArray(new String[refs.size()]);
            beanDefinition.setDependsOn(refArray);

            beanDefinition.setPropertyValues(pvs);


            // 处理构造器参数
            List<Element> constructors = element.elements("constructor-arg");
            ArgumentValues avs = new ArgumentValues();
            for (Element constructor : constructors) {
                String constructorType = constructor.attributeValue("type");
                String constructorName = constructor.attributeValue("name");
                String constructorValue = constructor.attributeValue("value");
                avs.addGenericArgumentValue(new ArgumentValue(constructorType,constructorName, constructorValue));
            }
            beanDefinition.setConstructorArgumentValues(avs);

            // 注册
            this.beanFactory.registerBeanDefinition(beanId,beanDefinition);
        }
    }


}
