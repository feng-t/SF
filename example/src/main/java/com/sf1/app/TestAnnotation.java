package com.sf1.app;

import com.sf.annotation.AnnotationManagement;
import com.sf.app.ApplicationContext;
import com.sf.bean.BeanFactory;
import com.sf.bean.FindBeanPath;
import com.sf.test1.Test1T;

import java.net.URL;
import java.util.Enumeration;

//@Ann
public class TestAnnotation {
    AppMain appMain;
    TestAnnotation(AppMain appMain,String str){
        this.appMain=appMain;
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        Test1T test1T = new Test1T();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = loader.getResources("");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url);
        }


//        URL url = new URL("/Users/hu/soft/apache-maven-3.6.2/mlib/com/alibaba/druid/1.1.17/druid-1.1.17.jar");
//        JarURLConnection con = (JarURLConnection) url.openConnection();
//        JarFile jarFile = con.getJarFile();
//        Enumeration<JarEntry> entries = jarFile.entries();
//        while (entries.hasMoreElements()) {
//            JarEntry entry = entries.nextElement();
//            String name = entry.getName();
//            System.out.println(name);
//        }


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
        return " TestAnnotation-- "+appMain+"\n";
    }
}
