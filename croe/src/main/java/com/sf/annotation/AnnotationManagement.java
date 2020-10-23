package com.sf.annotation;

import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.app.ApplicationContext;
import com.sf.bean.BeanFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解管理
 */
public class AnnotationManagement {
    private final ApplicationContext context;

    public AnnotationManagement(ApplicationContext context){
        this.context=context;
    }
    public Set<AbstractAnnotationHandler<?>> handlers= ConcurrentHashMap.newKeySet();

    private void addToHandler(AbstractAnnotationHandler<?> handler){
        handlers.add(handler);
    }

    /**
     * 处理
     */
    public void handler() throws Exception {
        BeanFactory factory = context.getFactory();
        for (Class<?> beanClass : factory.getBeanClass()) {
            if (AbstractAnnotationHandler.class.isAssignableFrom(beanClass)&&beanClass!=AbstractAnnotationHandler.class){
                addToHandler((AbstractAnnotationHandler<?>) factory.getBean(beanClass));
            }
        }
        Class<?>[] classes = factory.getBeanClass();
        for (Class<?> c : classes) {
            for (AbstractAnnotationHandler<?> handler : handlers) {
                handler.verify(c,factory);
            }
        }
    }
}
