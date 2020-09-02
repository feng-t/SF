package com.sf.core;

import com.sf.core.annotation.AbstractAnnotationHandler;
import com.sf.core.handler.DefaultExceptionHandler;
import com.sf.core.handler.ExceptionHandler;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    public List<AbstractAnnotationHandler> annohandlers = new ArrayList<>();

    public final Set<Class<?>> classList=new HashSet<>();
    //存放bean
    public final Map<Class<?>,Object> bean = new ConcurrentHashMap<>();
    public ExceptionHandler eh;
    /**
     *
     * @param aClass
     */
    public void precessAnnotation(Class<?> aClass) {
        if (!aClass.isAnnotation() && !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())) {
            try {
                if (ExceptionHandler.class.isAssignableFrom(aClass)) {
                    eh = (ExceptionHandler) aClass.newInstance();
                }
                bean.put(aClass, aClass.newInstance());
                classList.add(aClass);
            } catch (Exception ignore) {

            }
        }
    }

    public void addAnnHandler() throws IllegalAccessException, InstantiationException {
        Iterator<Class<?>> it = classList.iterator();
        while (it.hasNext()) {
            Class<?> aClass = it.next();
            if (AbstractAnnotationHandler.class.isAssignableFrom(aClass) && !aClass.isInterface()) {
                annohandlers.add((AbstractAnnotationHandler) aClass.newInstance());
                it.remove();
            }
        }
    }
    /**
     * 处理bean
     */
    public void actionBean() {
        Class<?> c = null;
        try {
            for (Class<?> aClass : classList) {
                c = aClass;
                Object o = bean.get(aClass);
                if (o == null) {
                    continue;
                }
                if (annohandlers.isEmpty()) {
                    AbstractAnnotationHandler h = new AbstractAnnotationHandler() {
                    };
                    h.action(c, o);
                } else {
                    for (AbstractAnnotationHandler handler : annohandlers) {
                        handler.action(aClass, o);
                    }
                }
            }
        } catch (Exception e) {
            handlerException(e, c);
        }
    }
    public void handlerException(Exception e,Class<?> aClass){
        if (eh == null) {
            eh = new DefaultExceptionHandler();
        }
        eh.action(e, aClass);
    }
}
