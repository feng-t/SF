package com.sf.bean.factory;

import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class BeanFactory {
    Map<Class<?>, Resource<?>> bean = new ConcurrentHashMap<>();
    Map<Class<? extends Annotation>, Resource<?>> annotationMap = new ConcurrentHashMap<>();
    public final Queue<Resource<?>> preLoad = new PriorityBlockingQueue<>();

    BeanFactory(Set<Resource<?>> resourceSet) throws ClassNotFoundException {
        for (Resource<?> resource : resourceSet) {
            bean.put(resource.getBeanClass(), resource);
            preLoad.add(resource);
        }
    }


    public abstract Object getBean(Class<?> targetClass);

    public abstract List<Class<?>> getBeanClass();

    public abstract List<Class<?>> getBeanClass(Class<?> c);

    public abstract void addResource(Set<Resource<?>> set);
}
