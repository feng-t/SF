package com.sf.app;

import com.sf.bean.BeanFactory;
import com.sf.bean.DefaultBeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    private BeanFactory factory;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            factory = new DefaultBeanFactory(clazz);
            factory.scanPath();
            //加载spi


            factory.preLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void run(Class<?> clazz, String[] ages) {
        new Application(clazz, ages).run();
    }

    public void run() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
