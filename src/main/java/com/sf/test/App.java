package com.sf.test;

import com.sf.core.annotation.fun.Fun;
import com.sf.core.app.Application;

public class App{
    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
    }
    @Fun
    public void t(){
        System.out.println("main test");
    }
}
