package com.sf.app;

import com.sf.bean.BeanFactory;

public class ApplicationContext {
    public BeanFactory factory;
    public ApplicationContext(BeanFactory factory){
        this.factory=factory;

    }

    public BeanFactory getFactory() {
        return factory;
    }
    public void load() throws Exception {
        factory.loadAllPreBean();
    }
}
