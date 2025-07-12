package com.jotaro.core.factory.impl;

import com.google.common.collect.Lists;
import com.jotaro.core.code.JotaroErrorCode;
import com.jotaro.core.enums.BasicType;
import com.jotaro.core.exception.JotaroSpringException;
import com.jotaro.core.factory.BeanFactory;
import com.jotaro.core.model.BeanDefinition;
import com.jotaro.core.model.args.ArgumentValue;
import com.jotaro.core.model.args.ArgumentValues;
import com.jotaro.core.model.props.PropertyValue;
import com.jotaro.core.model.props.PropertyValues;
import com.jotaro.regitsry.BeanDefinitionRegistry;
import com.jotaro.regitsry.impl.JotaroSingletonBeanRegistryImpl;
import java.lang.reflect.Method;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description beanFactory自定义实现类
 */
@NoArgsConstructor
public class JotaroBeanFactoryImpl extends JotaroSingletonBeanRegistryImpl implements BeanFactory, BeanDefinitionRegistry {

    /**
     * 声明Logger实例
     */
    private static final Logger logger = LogManager.getLogger(JotaroBeanFactoryImpl.class);

    /**
     * 替代beanName和 beanDefinition的集合
     */
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    /**
     * 存放beanDefinition的name(理论上==beanName)
     */
    private List<String> beanDefinitionNames = Lists.newArrayList();

    @Override
    public Object getBean(String beanName) {
        Object singletonBean = this.getSingleton(beanName);
        if(Objects.isNull(singletonBean)){
            // 若单例工厂里没有bean，则从提前曝光里找bean
            singletonBean = this.earlySingletons.get(beanName);

            // 若提前曝光都没有，则从beanDefinition中构建
            if(Objects.isNull(singletonBean)){
                logger.warn("提前曝光失效, beanDefinition构建,beanName: {}", beanName);
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                if(Objects.isNull(beanDefinition)){
                    throw new JotaroSpringException(JotaroErrorCode.BEAN_DEFINITION_NOT_REGISTER,beanName);
                }
                try{
                    singletonBean = this.createBean(beanDefinition);
                    this.registerSingleton(beanName,singletonBean);

                    // 预留beanpostprocessor位置
                    // step 1: postProcessBeforeInitialization
                    // step 2: afterPropertiesSet
                    // step 3: init-method
                    // step 4: postProcessAfterInitialization

                }catch (Exception e){
                    logger.error("初始化bean失败,e: {}",e.getMessage());
                    throw new JotaroSpringException(JotaroErrorCode.BEAN_INITIALIZATION_ERROR,beanName);
                }
            }
        }
        return singletonBean;
    }


    /**
     * core: 通过beanDefinition构造bean
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {
        // beanDefinition的反射类
        Class<?> clz = null;
        // bean实例
        Object singletonBean = this.doCreateBean(beanDefinition);
        this.earlySingletons.put(beanDefinition.getBeanId(), singletonBean);

        // 构造参数处理
        try{
            clz = Class.forName(beanDefinition.getClassName());
        }catch (Exception e){
            logger.error("createBean流程错误:{}",e.getMessage());
            throw new JotaroSpringException(e.getMessage());
        }

        // 处理bean依赖
        return handleProperties(beanDefinition,clz,singletonBean);
    }


    /**
     * 创建提前曝光bean
     * @param beanDefinition
     * @return
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object singletonBean = null;
        Constructor constructor = null;

        try{
            clz = Class.forName(beanDefinition.getClassName());
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues =   new Object[argumentValues.getArgumentCount()];
                for (int i = 0;  i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getArgumentValue(i);
                    BasicType type = BasicType.fromString(argumentValue.getType());
                    if(Objects.nonNull(type)){
                        paramValues[i] = type.parseOfValue(argumentValue.getValue());
                        paramTypes[i] = type.getClazz();
                    }else {
                        paramValues[i] = argumentValue.getValue();
                        paramTypes[i] = String.class;
                    }
                }
                try {
                    constructor = clz.getConstructor(paramTypes);
                    singletonBean = constructor.newInstance(paramValues);
                }catch (Exception e){
                    logger.error("提前曝光初始化异常, beanName:{}",beanDefinition.getBeanId());
                    throw new JotaroSpringException(JotaroErrorCode.BEAN_CREATE_ERROR,beanDefinition.getBeanId(),
                        Arrays.toString(paramTypes),Arrays.toString(paramValues));
                }
            }else {
                singletonBean = clz.newInstance();
            }

        }catch (Exception e){
            logger.error("提前曝光流程异常 beanName:{}",beanDefinition.getBeanId());
            throw new JotaroSpringException(e.getMessage());
        }
        return singletonBean;

    }

    /**
     * 处理构造参数和依赖注入
     * @param beanDefinition
     * @param clz
     * @param singletonBean
     */
    private Object handleProperties(BeanDefinition beanDefinition,Class<?> clz,Object singletonBean) {
        // 类属性处理
        try{
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (!propertyValues.isEmpty()) {
                for (int i = 0; i < propertyValues.size(); i++) {
                    //对每一个属性，分数据类型分别处理
                    PropertyValue propertyValue =
                        propertyValues.getPropertyValueList().get(i);
                    String propertyValueType = propertyValue.getType();
                    String propertyValueName = propertyValue.getName();
                    Object propertyValueValue = propertyValue.getValue();
                    boolean isRef = propertyValue.isReference();
                    Class<?>[] paramTypes = new Class<?>[1];
                    Object[] paramValues = {propertyValueValue};

                    if(!isRef){
                        BasicType type = BasicType.fromString(propertyValueType);
                        if(Objects.nonNull(type)){
                            paramTypes = new Class[]{type.getClazz()};
                        }else{
                            paramTypes = new Class[]{String.class};
                        }
                    }else{
                        // 若为bean依赖，则通过getBean拿到bean，再注入
                        try{
                            paramTypes[0] = Class.forName(propertyValueType);
                            paramValues[0] = getBean((String)propertyValueValue);
                        }catch (Exception e){
                            logger.error("<UNK>, beanName:{}",beanDefinition.getBeanId());
                        }
                    }

                    //按照set规范查找setter方法，调用setter方法设置属性
                    String methodName = "set" + propertyValueName.substring(0, 1).toUpperCase()
                        + propertyValueName.substring(1);
                    Method method = null;
                    try {
                        method = clz.getMethod(methodName, paramTypes);
                        method.invoke(singletonBean, paramValues);
                    }catch (Exception e){
                        throw new JotaroSpringException(e.getMessage());
                    }
                }
            }
        }catch (Exception e){
            logger.error("createBean property阶段:{}",e.getMessage());
            throw new JotaroSpringException(e.getMessage());
        }
        return singletonBean;

    }

    /**
     * 初始化IOC容器
     */
    public void refresh() {
        for(String beanName : beanDefinitionNames){
            this.getBean(beanName);
        }
    }

    /**
     * 只进行beanDefinition的注册
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getBeanId(),beanDefinition);
    }

    /**
     * 注册beanDefinition && 注册单例非懒加载bean
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName,beanDefinition);
        this.beanDefinitionNames.add(beanName);
        // 若非懒加载则直接注册singleton

    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }


    @Override
    public synchronized void removeBeanDefinition(String beanName) {
        this.beanDefinitions.remove(beanName);
        this.beanDefinitionNames.remove(beanName);
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
