package com.sf.core;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    public final Set<Class<?>> classList=new HashSet<>();
    public final Map<Class<?>,Object> bean = new ConcurrentHashMap<>();

}
