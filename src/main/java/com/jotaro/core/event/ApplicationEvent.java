package com.jotaro.core.event;

import java.util.EventObject;

/**
 * @author caihengJotaro
 * @date 2025/7/12
 * @description 自定义事件
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    public ApplicationEvent(Object arg0) { super(arg0); }

}
