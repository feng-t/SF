package com.sf.bean.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    //已经创建的bean
    Map<Class<?>,?> bean=new ConcurrentHashMap<>();

}
