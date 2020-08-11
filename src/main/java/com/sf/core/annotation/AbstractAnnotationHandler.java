package com.sf.core.annotation;

/**
 * 处理注解
 */
public interface AbstractAnnotationHandler {

    default void action(Class<?> c, Object obj){

    }
}
