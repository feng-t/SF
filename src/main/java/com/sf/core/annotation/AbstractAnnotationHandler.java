package com.sf.core.annotation;

import com.sf.core.BeanFactory;
import com.sf.core.app.Application;

/**
 * 处理注解
 * @author hu
 */
public interface AbstractAnnotationHandler {
    BeanFactory beanFactory = Application.getApp().beanFactory;

    /**
     * 调用
     * @param c class信息
     * @param obj 对象
     * @throws Exception
     */
    default void invoke(Class<?> c, Object obj) throws Exception {
    }
}
