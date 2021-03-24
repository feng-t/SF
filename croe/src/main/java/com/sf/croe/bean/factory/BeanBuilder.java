package com.sf.croe.bean.factory;

import com.sf.croe.bean.Resource;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class BeanBuilder<T> {
    public Set<Resource<?>> resources=new CopyOnWriteArraySet<>();
    public final Queue<Resource<?>> preLoad = new PriorityBlockingQueue<>();


    protected BeanBuilder(Set<Resource<?>> resources){
        this.resources.addAll(resources);
    }

    public abstract T createBean(Class<T> tClass);
}
