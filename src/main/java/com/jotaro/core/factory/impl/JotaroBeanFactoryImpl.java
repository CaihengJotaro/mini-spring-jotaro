package com.jotaro.core.factory.impl;

import com.jotaro.core.exception.JoatroSpringException;
import com.jotaro.core.code.JotaroErrorCode;
import com.jotaro.core.factory.BeanFactory;
import com.jotaro.core.model.BeanDefinition;
import com.jotaro.regitsry.impl.JotaroSingletonBeanRegistryImpl;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description beanFactory自定义实现类
 */
@NoArgsConstructor
public class JotaroBeanFactoryImpl extends JotaroSingletonBeanRegistryImpl implements BeanFactory {

    /**
     * 替代beanName和 beanDefinition的集合
     */
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        Object singletonBean = this.getSingleton(beanName);
        if(Objects.isNull(singletonBean)){
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if(Objects.nonNull(beanDefinition)){
                throw new JoatroSpringException(JotaroErrorCode.BEAN_DEFINITION_NOT_REGISTER,beanName);
            }
            try{
                singletonBean = Class.forName(beanDefinition.getClassName()).newInstance();
                this.registerSingleton(beanName,singletonBean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return singletonBean;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getBeanId(),beanDefinition);
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
    }
}
