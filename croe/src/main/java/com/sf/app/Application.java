package com.sf.app;

import com.sf.annotation.AnnotationManagement;
import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.bean.BeanFactory;
import com.sf.bean.FindBeanPath;
import com.sf.bean.Resource;
import com.sf.paraprocess.ProcessIncomingParameters;

import java.util.Set;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    public ApplicationContext applicationContext;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            FindBeanPath path = new FindBeanPath();
            final BeanFactory factory = new BeanFactory(path.scanPaths(clazz));
            applicationContext = new ApplicationContext(factory);
            applicationContext.load();
            //注解处理
            annotationHandler(path,new AnnotationManagement(applicationContext));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理注解
     */
    private void annotationHandler(FindBeanPath path,AnnotationManagement management) throws Exception {
        Set<Resource> set = path.scanPaths(AnnotationManagement.class);
        applicationContext.getFactory().addResource(set);
        management.handler();
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
