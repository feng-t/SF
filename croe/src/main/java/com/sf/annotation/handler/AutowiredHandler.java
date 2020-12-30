package com.sf.annotation.handler;

import com.sf.annotation.Autowired;

import java.lang.reflect.Field;

public class AutowiredHandler extends AbstractAnnotationHandler<Autowired> {
    @Override
    public Class<Autowired> getAnnotationClass() {
        return Autowired.class;
    }

    @Override
    public void process(Class<?> c, Object obj,Field field) throws Exception {
        field.setAccessible(true);
        ParentBeanFactory factory = getContext().getFactory();
        field.setAccessible(true);
        Object bean = factory.getBean(field.getType());
        if (bean!=null){
            field.set(obj,bean);
        }else {
            throw new ClassNotFoundException("没有找到："+field.getType());
        }

    }

}
