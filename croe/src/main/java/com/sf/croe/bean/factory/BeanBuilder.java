package com.sf.croe.bean.factory;

import com.sf.croe.bean.Resource;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class BeanBuilder<T> {
    public Set<Resource<?>> resources=new CopyOnWriteArraySet<>();
    public final Queue<Resource<?>> preLoad = new PriorityBlockingQueue<>();


    protected BeanBuilder(Set<String> resources){

    }

    public abstract T createBean(Class<T> tClass);


    private void createResources(String className) throws ClassNotFoundException {
        Class<?> name = Class.forName(className);
    }
    /**
     * 预处理bean，为创建bean做准备，包括解析注解，分析创建方式
     * @param resource
     */
    private void preHandlerBean(Resource<T>resource){
        if (!resource.contains(Resource.State.finish)){
            Class<T> beanClass = resource.getBeanClass();
        }
    }
}
