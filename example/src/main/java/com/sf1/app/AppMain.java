package com.sf1.app;

import com.sf.annotation.Autowired;
import com.sf.annotation.Bean;
import com.sf.annotation.Component;
import com.sf.test1.Test1;
import com.sf1.asm.Account;

public class AppMain {

    @Autowired
    private TestAnnotation a;
    @Autowired
    private Test1 test1;

    public static void main(String[] args){
//        Application.run(AppMain.class,args);
    }

    public static void t(){};
    @Override
    public String toString() {
        return "appMain toString";
    }
    @Bean
    public TestAnnotation get(){
        System.out.println("get: "+test1);
        return new TestAnnotation(this,"test");
    }

}