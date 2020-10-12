package com.sf.app;

import com.sf.bean.abstractBeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    private final Class<?> clazz;
    private final abstractBeanFactory beanFactory = new abstractBeanFactory() {
    };

    private Application(Class<?> clazz, String[] ages) {
        this.clazz = clazz;
        //扫描路径
        beanFactory.scanPaths(clazz);
        incomingParameters.process(ages);
        //加载spi

    }

    public static void run(Class<?> clazz, String[] ages) {
        new Application(clazz, ages).run();
    }

    public void run() {
    }

}
