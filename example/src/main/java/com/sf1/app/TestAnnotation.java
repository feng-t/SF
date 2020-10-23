package com.sf1.app;

import com.sf.annotation.AnnotationManagement;
import com.sf.app.ApplicationContext;
import com.sf.bean.BeanFactory;
import com.sf.bean.FindBeanPath;

//@Ann
public class TestAnnotation {
    AppMain appMain;
    TestAnnotation(AppMain appMain){
        this.appMain=appMain;
    }

    public static void main(String[] args) throws Exception {
        Class<?> c= TestAnnotation.class;

        final BeanFactory factory = new BeanFactory(new FindBeanPath().scanPaths(c));
        ApplicationContext context = new ApplicationContext(factory);
        factory.loadAllPreBean();
//        DefaultBeanFactory factory = new DefaultBeanFactory(c);
        AnnotationManagement management = new AnnotationManagement(context);
        management.handler();
    }

    @Override
    public String toString() {
        return " TestAnnotation-- "+appMain+"\n";
    }
}
