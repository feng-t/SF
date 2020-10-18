package com.sf.annotation.handler;

import java.lang.annotation.Annotation;

public interface AnnotationHandler<T extends Annotation> {
    /**
     * 获取注解的class
     * @return class<T>
     */
    Class<T> getAnnotationClass();

    /**
     * 验证是否存在注解
     * @param o
     */
    default void verify(Class<?> o) {
        T t = o.getAnnotation(getAnnotationClass());
        if (t != null) {
            process(o);
        }
    }
    /**
     * 有注解就添加进去
     * @param o
     */
    void process(Class<?> o);
}
