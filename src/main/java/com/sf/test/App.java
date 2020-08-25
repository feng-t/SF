package com.sf.test;

import com.sf.core.annotation.AutoWired;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.app.Application;

public class App{

    @AutoWired
    public static App app;
    @AutoWired
    private static TestErr tr;

    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
        System.out.println(app.toString());
        System.out.println(tr.app.toString());
    }

    @Fun
    public void test(){
        System.out.println("test");
    }
}
