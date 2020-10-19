package com.sf1.app;

import com.sf.annotation.Ann;
import com.sf.annotation.handler.AbstractAnnotationHandler;
import com.sf.annotation.test;
import com.sf.bean.DefaultBeanFactory;

@Ann
public class TestAnnotation {
    public static void main(String[] args) throws Exception {
        Class<?> c= TestAnnotation.class;
        DefaultBeanFactory factory = new DefaultBeanFactory(c);
        AbstractAnnotationHandler<?> test = new test(factory);
        test.verify(c);
    }

    @Override
    public String toString() {
        return " TestAnnotation";
    }
}
