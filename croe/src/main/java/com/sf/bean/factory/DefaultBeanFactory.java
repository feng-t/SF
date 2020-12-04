package com.sf.bean.factory;

import com.sf.annotation.Component;
import com.sf.bean.Resource;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;

import java.util.Set;

public class DefaultBeanFactory extends ParentBeanFactory{
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
