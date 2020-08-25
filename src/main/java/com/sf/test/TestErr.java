package com.sf.test;

import com.sf.core.annotation.AutoWired;
import com.sf.core.handler.ExceptionHandler;

public class TestErr extends ExceptionHandler {

    @AutoWired
    public App app;
    public String cu="test";

    @Override
    public void action(Exception e, Object obj) {
        e.printStackTrace();
        System.err.println(obj+" "+e.getMessage());
    }
}
