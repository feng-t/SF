package com.sf.bean.factory;

import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    public final Set<Resource> resources = ConcurrentHashMap.newKeySet();


    public BeanFactory(Set<Resource> resourceSet){
        resources.addAll(resourceSet);
        //进行分析
    }





}
