package com.sf.app;

import com.sf.annotation.AnnotationManagement;
import com.sf.bean.factory.DefaultBeanFactory;
import com.sf.bean.factory.ParentBeanFactory;
import com.sf.bean.FindBeanPath;
import com.sf.bean.Resource;
import com.sf.exception.ExceptionHandler;
import com.sf.exception.GlobalException;
import com.sf.paraprocess.ProcessIncomingParameters;

import java.util.List;
import java.util.Set;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    private ApplicationContext applicationContext;
    private FindBeanPath path;

    private List<Class<? extends GlobalException>> exception;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            path = new FindBeanPath();
            final ParentBeanFactory factory = new DefaultBeanFactory(path.scanPaths(clazz));
            applicationContext =  ApplicationContext.initInstance(factory);
            scanException(factory);
            //
        } catch (Exception e) {
            handlerException(e);
        }

    }

    private void scanException(ParentBeanFactory factory) throws Exception {
        exception = factory.getBeanClass(GlobalException.class);
    }

    /**
     * 处理注解
     */
    private void annotationHandler(FindBeanPath path, AnnotationManagement management) throws Exception {
        Set<Resource> set = path.scanPaths(AnnotationManagement.class);
        applicationContext.getFactory().addResource(set);
        management.handler();
    }

    public static void run(Class<?> clazz, String[] ages) {
        new Application(clazz, ages).run();
    }

    public void run() {
        try {
            applicationContext.load();
            //注解处理
            annotationHandler(path, AnnotationManagement.getInstance(applicationContext));
        } catch (Exception e) {
            handlerException(e);
        }
    }

    /**
     * 全局异常处理
     * @param e
     */
    private <T extends Exception>  void handlerException(T e){
        if (exception != null) {
            for (Class<? extends GlobalException> ex : exception) {
                ExceptionHandler handler = ex.getAnnotation(ExceptionHandler.class);
                if(handler!=null&&handler.value() == e.getClass()){
                    try {
                        final GlobalException bean = applicationContext.getFactory().getBean(ex);
                        bean.process(e);
                        return;
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
        e.printStackTrace();
    }

}
