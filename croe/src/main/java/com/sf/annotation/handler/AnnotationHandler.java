package com.sf.annotation.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface AnnotationHandler {
    /**
     * 有注解就添加进去
     *
     * @param c
     */
    default void process(Class<?> c, Object obj) throws Exception {

    }
    default void process(Class<?> c, Object obj, Method method) throws Exception {
        method.setAccessible(true);
    }

    default void process(Class<?> c, Object obj, Field field) throws Exception {
        field.setAccessible(true);
    }
}
