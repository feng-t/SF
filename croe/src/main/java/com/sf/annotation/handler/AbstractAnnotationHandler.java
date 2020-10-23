package com.sf.annotation.handler;

import com.sf.bean.BeanFactory;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotationHandler<T extends Annotation> {
    /**
     * 获取注解的class
     * @return class<T>
     */
    public abstract Class<T> getAnnotationClass();


    /**
     * 有注解就添加进去
     * @param c
     */
   public abstract void process(Class<?> c,Object obj,T t);
    /**
     * 验证是否存在注解
     * @param o
     */
    public void verify(Class<?> o,BeanFactory factory) throws Exception {
        //获取到注解
        T t = o.getAnnotation(getAnnotationClass());
        if (t != null) {
            process(o,factory.getBean(o),  t);
        }
    }
}
