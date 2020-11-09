package com.sf.bean.factory;

import com.sf.bean.Resource;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;

import java.util.Set;

public class DefaultBeanFactory extends ParentBeanFactory{
    public DefaultBeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        super(resourceSet);
    }

    @Override
    public Object loadBean(Resource resource) throws Exception {
        return super.loadBean(resource);
    }

    @Override
    public Object loadBean(Class<?> preBean) throws Exception {
        return super.loadBean(preBean);
    }
}
