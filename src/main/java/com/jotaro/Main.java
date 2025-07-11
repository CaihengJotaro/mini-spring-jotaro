package com.jotaro;

import com.jotaro.minibean.DemoBean;
import com.jotaro.context.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mini-bean.xml");
        DemoBean bean = (DemoBean)context.getBean("demoBean");
        bean.demoTest();
        
    }
}