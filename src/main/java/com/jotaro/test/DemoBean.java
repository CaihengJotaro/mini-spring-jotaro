package com.jotaro.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description beanDemo 例子
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean {

    private String name;

    private int number;

    private String message;

    private DemoBeanDep demoBeanDep;

    public DemoBean(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void demoTest(){
        System.out.println("hello jotaro-Spring");
    }

    
}
