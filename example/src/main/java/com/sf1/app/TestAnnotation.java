package com.sf1.app;

import com.sf.annotation.Ann;
import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.annotation.test;
import com.sf.bean.BeanFactory;
import com.sf.bean.DefaultBeanFactory;
import com.sf.bean.FindBeanPath;

@Ann
public class TestAnnotation {
    AppMain appMain;
    TestAnnotation(AppMain appMain){
        this.appMain=appMain;
        System.out.println(" TestAnnotation ");
    }

    public static void main(String[] args) throws Exception {
        Class<?> c= TestAnnotation.class;

        final BeanFactory factory = new BeanFactory(new FindBeanPath().scanPaths(c));
        factory.loadAllPreBean();
//        DefaultBeanFactory factory = new DefaultBeanFactory(c);
        AbstractAnnotationHandler<?> test = new test(factory);
        test.verify(c);
    }

    @Override
    public String toString() {
        return " TestAnnotation "+appMain+"\n";
    }
}
