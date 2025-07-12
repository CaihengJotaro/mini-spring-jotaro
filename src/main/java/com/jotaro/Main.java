package com.jotaro;

import com.jotaro.test.DemoBean;
import com.jotaro.context.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("easy-bean.xml",true);
        DemoBean bean = (DemoBean)context.getBean("demoBean");
        bean.demoTest();
        System.out.println(bean.getDemoBeanDep().getDemoBean() == bean);
        
    }
}