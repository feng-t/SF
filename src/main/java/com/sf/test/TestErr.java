package com.sf.test;

import com.sf.core.handler.ExceptionHandler;

public class TestErr extends ExceptionHandler {
    @Override
    public void action(Exception e, Object obj) {
        System.err.println(obj+" "+e.getMessage());
    }
}
