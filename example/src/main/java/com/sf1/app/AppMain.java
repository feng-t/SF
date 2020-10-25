package com.sf1.app;

import com.sf.annotation.Bean;
import com.sf.app.Application;

@Ann
public class AppMain {
    public static void main(String[] args){
        Application.run(AppMain.class,args);
    }

    @Override
    public String toString() {
        return "appMain toString";
    }
    @Bean
    public TestAnnotation get(){
        return new TestAnnotation(this,"test");
    }
}