package com.sf.bean.factory;

import com.sf.annotation.Component;
import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.*;

public class DefaultBeanFactory extends ParentBeanFactory{
    private final Map<Class<Annotation>,List<Class<?>>> annotationMap=new HashMap<> ();
    public DefaultBeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        super(resourceSet);
        List<Resource> filter = getFilter(Component.class);
        for (Resource resource : filter) {
            annotationMap.getOrDefault(Component.class,new ArrayList<>()).add(resource.getBeanClass());
        }
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

    public List<Resource> getFilter(Class<? extends Annotation> annotation) throws ClassNotFoundException {
        List<Resource> resources = new ArrayList<>();
        for (Resource resource : this.resources) {
            if(findAnnotation.isContains(resource.getBeanClass(),annotation)){
                resources.add(resource);
            }
        }
        return resources;
    }



}
