package com.sf.app;

import com.sf.bean.BeanFactory;
import com.sf.bean.FindBeanPath;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    public ApplicationContext applicationContext;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            final BeanFactory factory = new BeanFactory(new FindBeanPath().scanPaths(clazz));
            applicationContext = new ApplicationContext(factory);
            //加载spi
            applicationContext.load();
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
