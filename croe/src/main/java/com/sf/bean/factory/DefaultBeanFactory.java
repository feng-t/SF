package com.sf.bean.factory;

import com.sf.annotation.Autowired;
import com.sf.annotation.Component;
import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.*;

public class DefaultBeanFactory extends ParentBeanFactory{
    private final Map<Class<Annotation>,List<Class<?>>> annotationMap=new HashMap<> ();
    public DefaultBeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        super(resourceSet);

    }



    /**
     * 只有声明了 {@link Component} 的类才会被加载,
     * @param resource
     * @return
     * @throws Exception
     */
    @Override
    public Object loadBean(Resource resource) throws Exception {
        return super.loadBean(resource);
    }




}
