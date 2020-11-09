package com.sf.app;

import com.sf.bean.factory.ParentBeanFactory;

public class ApplicationContext {
    public ParentBeanFactory factory;
    public ApplicationContext(ParentBeanFactory factory){
        this.factory=factory;

    }

    public ParentBeanFactory getFactory() {
        return factory;
    }
    public void load() throws Exception {
        factory.loadAllPreBean();
    }
}
