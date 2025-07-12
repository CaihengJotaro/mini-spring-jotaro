package com.jotaro.test;

import org.junit.Test;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description
 */
public class TestDemo {

    @Test
    public void testClass() throws ClassNotFoundException {
        String str = "String";
        System.out.println(Class.forName(str));
    }
}
