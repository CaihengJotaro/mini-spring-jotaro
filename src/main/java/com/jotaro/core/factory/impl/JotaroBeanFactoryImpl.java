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
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if(Objects.nonNull(beanDefinition)){
                throw new JotaroSpringException(JotaroErrorCode.BEAN_DEFINITION_NOT_REGISTER,beanName);
            }
            try{
                singletonBean = Class.forName(beanDefinition.getClassName()).newInstance();
                this.registerSingleton(beanName,singletonBean);
            }catch (Exception e){
                logger.info("初始化bean失败,e: {}",e.getMessage());
                throw new JotaroSpringException(JotaroErrorCode.BEAN_INITIALIZATION_ERROR,beanName);
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
        Object singletonBean = null;
        // 反射构造器
        Constructor<?> constructor = null;

        // 构造参数处理
        try{
            clz = Class.forName(beanDefinition.getClassName());
            ArgumentValues args = beanDefinition.getConstructorArgumentValues();
            Object[] paramValues = new Object[args.getArgumentCount()];
            if(args.isNotEmpty()){
                Class<?>[] paramTypes = new Class<?>[args.getArgumentCount()];
                for(int i = 0; i < args.getArgumentCount(); i++){
                    ArgumentValue arg = args.getArgumentValue(i);
                    BasicType type = BasicType.fromString(arg.getType());
                    if(Objects.nonNull(type)){
                        paramTypes[i] = type.getClazz();
                        paramValues[i] = type.parseOfValue(arg.getValue());
                    }else{
                        // 兜底String类型
                        paramTypes[i] = String.class;
                        paramValues[i] = arg.getValue();
                    }
                }
                // 有参构造bean
                try{
                    constructor = clz.getConstructor(paramTypes);
                    singletonBean = constructor.newInstance(paramValues);
                }catch (Exception e){
                    throw new JotaroSpringException(JotaroErrorCode.BEAN_CREATE_ERROR,beanDefinition.getBeanId(),
                        Arrays.toString(paramTypes),Arrays.toString(paramValues));
                }

            }else {
                // 无参构造
                singletonBean = clz.newInstance();
            }



        }catch (Exception e){
            logger.error("createBean流程错误:{}",e.getMessage());
            throw new JotaroSpringException(e.getMessage());
        }

        // 类属性处理
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                //对每一个属性，分数据类型分别处理
                PropertyValue propertyValue =
                    propertyValues.getPropertyValueList().get(i);
                String propertyValueType = propertyValue.getType();
                String propertyValueName = propertyValue.getName();
                Object propertyValueValue = propertyValue.getValue();
                Class<?>[] paramTypes = null;
                Object[] paramValues = {propertyValueValue};
                BasicType type = BasicType.fromString(propertyValueType);
                if(Objects.nonNull(type)){
                    paramTypes = new Class[]{type.getClazz()};
                }else{
                    paramTypes = new Class[]{String.class};
                }


                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + propertyValueName.substring(0, 1).toUpperCase()
                    + propertyValueName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                    method.invoke(singletonBean, paramValues);
                }catch (Exception e){
                    throw new JotaroSpringException(JotaroErrorCode.BEAN_INVOKE_SETTER_FUNCTION_ERROR,
                        beanDefinition.getBeanId(),propertyValueName,propertyValueValue);
                }
            }
        }

        return singletonBean;
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
        if(!beanDefinition.isLazyInit()){
            getBean(beanName);
        }
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
