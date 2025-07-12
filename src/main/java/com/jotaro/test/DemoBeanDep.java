package com.jotaro.test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description
 */
@Data
public class DemoBeanDep {

  private String name;

  private String age;

  private DemoBean demoBean;

  public DemoBeanDep(String name, String age) {
    this.name = name;
    this.age = age;
  }
}
