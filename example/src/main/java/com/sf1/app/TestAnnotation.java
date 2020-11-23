package com.sf1.app;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//@Ann
public class TestAnnotation {
    AppMain appMain;
    TestAnnotation(AppMain appMain,String str){
        this.appMain=appMain;
    }

//    public static void main(String[] args) throws Exception {
//        Class<?> c= TestAnnotation.class;
//
//        final BeanFactory factory = new BeanFactory(new FindBeanPath().scanPaths(c));
//        ApplicationContext context = new ApplicationContext(factory);
//        factory.loadAllPreBean();
////        DefaultBeanFactory factory = new DefaultBeanFactory(c);
//        AnnotationManagement management = new AnnotationManagement(context);
//        management.handler();
//    }

    @Override
    public String toString() {
        return "TestAnnotation-- "+appMain+"\n";
    }

    public static void main(String[] args) {
        System.out.println("---------------------------------");
        Hello h= (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(),
                new Class[]{Hello.class}
                ,(proxy, method, par)->{
                    System.out.println(method);

                    return "test";
                });
        String say = h.say("test");
        System.out.println("---------------------------------");
    }
}
