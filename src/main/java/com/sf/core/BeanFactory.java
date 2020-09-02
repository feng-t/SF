package com.sf.core;

import com.sf.core.annotation.AbstractAnnotationHandler;
import com.sf.core.handler.DefaultExceptionHandler;
import com.sf.core.handler.ExceptionHandler;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    /**
     * 注解处理
     */
    public List<AbstractAnnotationHandler> annotationHandler = new ArrayList<>();

    public final Set<Class<?>> classList=new HashSet<>();
    /**
     * 存放bean
     */
    public final Map<Class<?>,Object> beanMap = new ConcurrentHashMap<>();
    public ExceptionHandler exceptionHandler;
    /**
     * 实例化对象，并且添加到beanMap中
     * @param aClass class
     */
    public void precessAnnotation(Class<?> aClass) {
        if (!aClass.isAnnotation() && !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())) {
            try {
                if (ExceptionHandler.class.isAssignableFrom(aClass)) {
                    exceptionHandler = (ExceptionHandler) aClass.newInstance();
                }
                beanMap.put(aClass, aClass.newInstance());
                classList.add(aClass);
            } catch (Exception e) {
                System.err.print("error Class: "+aClass.getName()+" message: "+e.getMessage());
            }
        }
    }

    /**
     * 注解处理
     */
    public void addAnnotationHandler() throws IllegalAccessException, InstantiationException {
        Iterator<Class<?>> it = classList.iterator();
        while (it.hasNext()) {
            Class<?> aClass = it.next();
            if (AbstractAnnotationHandler.class.isAssignableFrom(aClass) && !aClass.isInterface()) {
                annotationHandler.add((AbstractAnnotationHandler) aClass.newInstance());
                it.remove();
            }
        }
    }

    public void invokeAnnotationHandler() {
        Class<?> c = null;
        try {
            for (Class<?> aClass : classList) {
                c = aClass;
                Object o = beanMap.get(aClass);
                if (o == null) {
                    continue;
                }
                if (annotationHandler.isEmpty()) {
                    AbstractAnnotationHandler h = new AbstractAnnotationHandler() {
                    };
                    h.action(c, o);
                } else {
                    for (AbstractAnnotationHandler handler : annotationHandler) {
                        handler.action(aClass, o);
                    }
                }
            }
        } catch (Exception e) {
            handlerException(e, c);
        }
    }
    public void handlerException(Exception e,Class<?> aClass){
        if (exceptionHandler == null) {
            exceptionHandler = new DefaultExceptionHandler();
        }
        exceptionHandler.action(e, aClass);
    }
}
