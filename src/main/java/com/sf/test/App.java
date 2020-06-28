package com.sf.test;

import com.sf.core.annotation.AutoWired;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.app.Application;
import com.sf.test.m1.Test;

public class App{

    @AutoWired
    private Test test;
    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
        System.out.println();
    }
    @Fun
    public void t(){
        System.out.println(test.str);
    }
}
