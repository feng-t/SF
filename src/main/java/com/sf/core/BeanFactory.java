package com.sf.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    public final Set<Class<?>> classList=new HashSet<>();
    public final Map<Class<?>,Object> bean = new HashMap<>();
}
