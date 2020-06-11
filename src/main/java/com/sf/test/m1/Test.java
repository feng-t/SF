package com.sf.test.m1;

import com.sf.core.annotation.fun.Fun;

public class Test {
    static {
        System.out.println("load test");
    }
    @Fun
    public void test(){
        System.out.println("test");
    }
}
