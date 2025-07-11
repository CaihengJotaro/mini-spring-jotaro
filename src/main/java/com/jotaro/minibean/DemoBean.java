package com.jotaro.minibean;

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

    public void demoTest(){
        System.out.println("hello miniSpring");
    }
    
}
