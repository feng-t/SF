package com.sf.app;

import com.sf.bean.factory.BeanFactory;
import com.sf.exception.NotFoundFactory;

public class ApplicationContext {
    public BeanFactory factory;
    public static ApplicationContext getInstance(){
        return singleton.context.getApplicationContext(null);
    }
    public static ApplicationContext initInstance(BeanFactory factory){
       return singleton.context.getApplicationContext(factory);
    }

    private ApplicationContext(BeanFactory factory){
        this.factory=factory;
    }

    public BeanFactory getFactory() {
        return factory;
    }
    public void load() throws Exception {
//        factory.loadAllPreBean();
//        加载bean
    }

    /**
     * 单例模式
     */
    private enum singleton{
        context;
        private ApplicationContext applicationContext;

        public ApplicationContext getApplicationContext(BeanFactory factory) {
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
