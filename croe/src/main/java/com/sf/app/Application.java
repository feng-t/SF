package com.sf.app;

import com.sf.bean.DefaultBeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    public ApplicationContext applicationContext;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            applicationContext = new ApplicationContext(new DefaultBeanFactory(clazz));
            applicationContext.scanPath();
            //加载spi

            applicationContext.preLoad();
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
