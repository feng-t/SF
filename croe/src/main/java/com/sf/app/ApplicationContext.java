package com.sf.app;

import com.sf.bean.BeanFactory;

import java.io.IOException;

public class ApplicationContext {
    public BeanFactory factory;
    public ApplicationContext(BeanFactory factory){
        this.factory=factory;
    }

    public BeanFactory getFactory() {
        return factory;
    }

    public void scanPath() throws IOException {
        factory.scanPath();
    }

    public void preLoad() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        factory.preLoad();
    }
}
