package com.sf.annotation;

import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.bean.BeanFactory;

public class test extends AbstractAnnotationHandler<Ann> {

    public test(BeanFactory factory){
        super(factory);
    }

    @Override
    public Class<Ann> getAnnotationClass() {
        return Ann.class;
    }

    @Override
    public void process(Class<?> c, Object obj, Ann ann) {

    }
}
