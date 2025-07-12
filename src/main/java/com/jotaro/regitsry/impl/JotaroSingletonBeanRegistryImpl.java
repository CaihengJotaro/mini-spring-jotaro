package com.jotaro.regitsry.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jotaro.regitsry.SingletonBeanRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description 自定义regitstry
 */
public class JotaroSingletonBeanRegistryImpl implements SingletonBeanRegistry {

    /**
     * IOC容器中的beanNames
     */
    protected List<String> beanNames = Lists.newArrayList();

    /**
     * 单例工厂,线程安全，一级缓存
     */
    protected Map<String, Object> singletons = new ConcurrentHashMap<>();

    /**
     * 二级缓存，提前曝光
     */
    protected Map<String,Object> earlySingletons = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.singletons.put(beanName, singletonObject);
        this.beanNames.add(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[])beanNames.toArray();
    }

    /**
     * 移除bean，线程安全
     * @param beanName
     */
    synchronized protected void  removeSingleton(String beanName) {
        this.beanNames.remove(beanName);
        this.singletons.remove(beanName);
    }
}
