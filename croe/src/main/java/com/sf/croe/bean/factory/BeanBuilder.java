package com.sf.croe.bean.factory;

import com.sf.annotation.bean.Bean;
import com.sf.annotation.croe.InitAnnotate;
import com.sf.croe.bean.Resource;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class BeanBuilder<T> {
    public Map<Class<?>,Resource<?>> resources=new ConcurrentHashMap<>();
    public final Queue<Resource<?>> preLoad = new PriorityBlockingQueue<>();


    protected BeanBuilder(Set<String> classNames) throws ClassNotFoundException {
        for (String className : classNames) {
            Resource<Object> resource = new Resource<>(className);
            resources.put(resource.getBeanClass(),resource);
        }
    }

    public abstract T createBean(Class<T> tClass);


    private void setPriority(Resource<T>resource) throws ClassNotFoundException {
        if (!resource.contains(Resource.State.finish)){
            //定优先级
            if (resource.findClassAnnotation(InitAnnotate.class)){
                resource.addState(Resource.State.basis);
            }
        }
    }
    /**
     * 预处理bean，为创建bean做准备，包括解析注解，分析创建方式
     * @param resource
     */
    private void preHandlerBean(Resource<T>resource){
        if (!resource.contains(Resource.State.finish)){
            Class<T> beanClass = resource.getBeanClass();
            //根据配置选择创建方式

        }
    }
}
