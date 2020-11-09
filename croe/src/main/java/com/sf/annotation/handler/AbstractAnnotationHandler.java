package com.sf.annotation.handler;

import com.sf.app.ApplicationContext;
import com.sf.bean.factory.ParentBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractAnnotationHandler<T extends Annotation> implements AnnotationHandler{
    private ApplicationContext context;

    /**
     * 获取注解的class
     * @return class<T>
     */
    public abstract Class<T> getAnnotationClass();



    /**
     * 验证是否存在注解
     * @param targetClass
     */
    public void verify(Class<?> targetClass, ParentBeanFactory factory) throws Exception {
        //获取到注解
        Class<T> annotationClass = getAnnotationClass();
        Object bean = factory.getBean(targetClass);
        if (targetClass.isAnnotationPresent(annotationClass)) {
            process(targetClass,bean);
        }
        for (Method method : targetClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)){
                process(targetClass,bean,method);
            }
        }
        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)){
                process(targetClass,bean,field);
            }
        }

    }

    public void addContext(ApplicationContext context){
        this.context=context;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
