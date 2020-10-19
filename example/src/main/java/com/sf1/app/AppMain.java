package com.sf1.app;

import com.sf.app.Application;

public class AppMain {
    public AppMain(TestAnnotation t){

    }
    public static void main(String[] args){
        Application.run(AppMain.class,args);
    }

    @Override
    public String toString() {
        return "appMain toString";
    }
}
