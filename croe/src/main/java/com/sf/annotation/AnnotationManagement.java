package com.sf.annotation;

import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.app.ApplicationContext;
import com.sf.bean.factory.ParentBeanFactory;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解管理
 */
public class AnnotationManagement {

    private static AnnotationManagement annotationManagement;

    public static AnnotationManagement getInstance(ApplicationContext context) {
        synchronized (AnnotationManagement.class){
            if (annotationManagement==null){
                synchronized (AnnotationManagement.class) {
                    annotationManagement = new AnnotationManagement(context);
                }
            }
        }
        return annotationManagement;
    }
    private final ApplicationContext context;

    private AnnotationManagement(ApplicationContext context) {
        this.context = context;
    }


    public Set<AbstractAnnotationHandler<?>> handlers = ConcurrentHashMap.newKeySet();

    private void addToHandler(AbstractAnnotationHandler<?> handler) {
        handlers.add(handler);
    }

    /**
     * 处理
     */
    public void handler() throws Exception {
        ParentBeanFactory factory = context.getFactory();
        for (Class<?> beanClass : factory.getBeanClass()) {
            if (AbstractAnnotationHandler.class.isAssignableFrom(beanClass) && beanClass != AbstractAnnotationHandler.class) {
                AbstractAnnotationHandler<?> handler = (AbstractAnnotationHandler<?>) factory.getBean(beanClass);
                handler.addContext(context);
                addToHandler(handler);
            }
        }
        List<Class<?>> classes = factory.getBeanClass();
        for (Class<?> c : classes) {
            if (!c.isEnum()
                    && c!=AnnotationManagement.class
                    && !c.isAnnotation()
                    && !Modifier.isAbstract(c.getModifiers())) {
                for (AbstractAnnotationHandler<?> handler : handlers) {
                    handler.verify(c, factory);
                }
            }
        }
    }
}
