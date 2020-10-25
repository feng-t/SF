package com.sf.app;

import com.sf.annotation.AnnotationManagement;
import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.bean.BeanFactory;
import com.sf.bean.FindBeanPath;
import com.sf.bean.Resource;
import com.sf.exception.ExceptionHandler;
import com.sf.exception.GlobalException;
import com.sf.paraprocess.ProcessIncomingParameters;

import java.util.List;
import java.util.Set;

public class Application {
    private final ProcessIncomingParameters incomingParameters = new ProcessIncomingParameters();
    public ApplicationContext applicationContext;
    public FindBeanPath path;
    private List<Class<? extends GlobalException>> exception;

    private Application(Class<?> clazz, String[] ages) {
        incomingParameters.process(ages);
        try {
            path = new FindBeanPath();
            final BeanFactory factory = new BeanFactory(path.scanPaths(clazz));
            applicationContext = new ApplicationContext(factory);
            scanException();
            //
        } catch (Exception e) {
            handlerException(e);
        }

    }

    private void scanException() throws Exception {
        exception = applicationContext.getFactory().getBeanClass(GlobalException.class);
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
            annotationHandler(path, new AnnotationManagement(applicationContext));
        } catch (Exception e) {
            handlerException(e);
        }
    }

    /**
     * 全局异常处理
     * @param e
     */
    private void handlerException(Exception e){
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
