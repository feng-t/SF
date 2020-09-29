package com.sf.demo;

import com.sf.core.annotation.AutoWired;
import com.sf.core.handler.AbstractExceptionHandler;

public class TestErr extends AbstractExceptionHandler {

    @AutoWired
    public App app;
    public String cu="test";

    @Override
    public void action(Exception e, Object obj) {
        e.printStackTrace();
        System.err.println(obj+" "+e.getMessage());
    }
}
