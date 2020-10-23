package com.sf1.app;

import com.sf.annotation.handler.AbstractAnnotationHandler;

public class test extends AbstractAnnotationHandler<Ann> {
    @Override
    public Class<Ann> getAnnotationClass() {
        return Ann.class;
    }

    @Override
    public void process(Class<?> c, Object obj, Ann ann) {
        System.out.println("执行了");
    }
}
