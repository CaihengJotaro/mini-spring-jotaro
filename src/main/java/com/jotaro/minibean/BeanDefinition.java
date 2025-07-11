package com.jotaro.minibean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author caihengJotaro
 * @date 2025/7/11
 * @description beanDefinition
 */


@Data
@AllArgsConstructor
public class BeanDefinition {

    // 一般为beanName
    private String id;

    // 全类名
    private String className;
    
}
