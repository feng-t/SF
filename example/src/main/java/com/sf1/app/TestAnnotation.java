package com.sf1.app;

import com.sf.annotation.handler.AnnotationHandler;
import com.sf.annotation.test;
import com.sf.app.Application;

public class TestAnnotation {
    public static void main(String[] args) {
        Class<?> c= Application.class;
        AnnotationHandler<?> test = new test();
        test.verify(c);
    }
}
