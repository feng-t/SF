package com.sf.app;

import com.sf.bean.AbstractBeanFactory;
import com.sf.bean.DefaultBeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    private final Class<?> clazz;
    private final AbstractBeanFactory beanFactory;

    private Application(Class<?> clazz, String[] ages) {
        this.clazz = clazz;
        beanFactory=new DefaultBeanFactory();
        beanFactory.scanPaths(clazz);
        incomingParameters.process(ages);
        //加载spi

    }

    public static void run(Class<?> clazz, String[] ages) {
        new Application(clazz, ages).run();
    }

    public void run() {
        try {
            beanFactory.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
