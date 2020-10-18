package com.sf.app;

import com.sf.bean.DefaultBeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

import java.io.IOException;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();

    private Application(Class<?> clazz, String[] ages) {
        try {
            DefaultBeanFactory factory = new DefaultBeanFactory(clazz);
            factory.scanPath();
        }catch (IOException e){
            e.printStackTrace();
        }
        incomingParameters.process(ages);
        //加载spi

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
