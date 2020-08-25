package com.sf.core.annotation;

import com.sf.core.BeanFactory;
import com.sf.core.app.Application;

/**
 * 处理注解
 */
public interface AbstractAnnotationHandler {
    BeanFactory beanFactory = Application.beanFactory;

    default void action(Class<?> c, Object obj) throws Exception {

    }
}
