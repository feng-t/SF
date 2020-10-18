package com.sf.annotation;

import com.sf.annotation.handler.AnnotationHandler;

public class test implements AnnotationHandler<Ann> {
    @Override
    public Class<Ann> getAnnotationClass() {
        return Ann.class;
    }

    @Override
    public void process(Class<?> o) {

    }

}
