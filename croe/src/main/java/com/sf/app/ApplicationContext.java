package com.sf.app;

import com.sf.bean.factory.ParentBeanFactory;
import com.sf.exception.NotFoundFactory;

public class ApplicationContext {
    public ParentBeanFactory factory;
    public static ApplicationContext getInstance(){
        return singleton.context.getApplicationContext(null);
    }
    public static ApplicationContext initInstance(ParentBeanFactory factory){
       return singleton.context.getApplicationContext(factory);
    }

    private ApplicationContext(ParentBeanFactory factory){
        this.factory=factory;
    }

    public ParentBeanFactory getFactory() {
        return factory;
    }
    public void load() throws Exception {
        factory.loadAllPreBean();
    }

    /**
     * 单例模式
     */
    private enum singleton{
        context;
        private ApplicationContext applicationContext;

        public ApplicationContext getApplicationContext(ParentBeanFactory factory) {
            if (applicationContext==null){
                if (factory==null){
                    throw new NotFoundFactory("factory is null");
                }
                applicationContext=new ApplicationContext(factory);
            }
            return applicationContext;
        }
    }
}
