package com.sf.croe.bean.factory;

import com.sf.croe.bean.Resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class BeanBuilder<T> {
    public Set<Resource<?>> resources=new CopyOnWriteArraySet<>();

    protected BeanBuilder(){}

    public abstract T createBean(Class<T> tClass);
}
