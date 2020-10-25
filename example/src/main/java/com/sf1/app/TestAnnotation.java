package com.sf1.app;

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
}
