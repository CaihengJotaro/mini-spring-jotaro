package com.jotaro.context;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jotaro.minibean.BeanDefinition;

/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description xml文件解析器
 */
public class ClassPathXmlApplicationContext {
    // beanDefinition列表
    private List<BeanDefinition>beanDefinitions = new ArrayList<>();
    // bean单例工厂（一级缓存）
    private Map<String,Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName){
        this.readXml(fileName);
        this.instanceBeans();
    }

    private void readXml(String fileName){
        SAXReader reader = new SAXReader();
        try { 
            URL xmlPath =this.getClass().getClassLoader().getResource(fileName); 
            Document document = reader.read(xmlPath); 
            Element rootElement = document.getRootElement(); //对配置文件中的每一个，进行处理 
            for (Element element : (List<Element>) rootElement.elements()) {
                //获取Bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
                //将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void instanceBeans(){
        for (BeanDefinition beanDefinition : beanDefinitions) {
             try { 
                singletons.put(beanDefinition.getId(),Class.forName(beanDefinition.getClassName()).newInstance()); 
            }catch (Exception e) {
                e.printStackTrace();
            }
         }
    }

    public Object getBean(String beanName){
        return singletons.get(beanName);
    }
}
