package com.sf.app;

import com.sf.bean.BeanFactory;
import com.sf.paraprocess.ProcessIncomingParameters;

public class Application {
    private final ProcessIncomingParameters incomingParameters=new ProcessIncomingParameters();
    private final Class<?> clazz;
    private final BeanFactory beanFactory=new BeanFactory();
    private Application(Class<?> clazz,String[] ages) throws Exception{
        incomingParameters.process(ages);
        this.clazz=clazz;
        //扫描路径
        beanFactory.scanPaths(clazz);
        //加载spi
    }
    public static void run(Class<?> clazz, String[] ages) throws Exception {
        new Application(clazz,ages).run();
    }
    public void run() throws Exception {
    }

}
