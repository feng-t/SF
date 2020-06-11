package com.sf.test;

import com.sf.core.annotation.fun.Fun;
import com.sf.core.app.Application;

public class App{
    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
//        System.out.println(s.substring(0,s.lastIndexOf(App.class.getSimpleName())-1));
////        Enumeration<URL> resources = App.class.getClassLoader().getResources("com/sf/");
////        while (resources.hasMoreElements()){
////            URL url = resources.nextElement();
////            System.out.println(url);
////        }
    }
    @Fun
    public void t(){
        System.out.println("main test.properties");
    }
}
