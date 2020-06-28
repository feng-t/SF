package com.sf.test;

import com.sf.core.handler.ExceptionHandler;
import com.sf.test.m1.Test;

import java.lang.reflect.Field;

public class TestErr extends ExceptionHandler {

    private Test test;
//    public static void main(String[] args) throws Exception {
//        TestErr err = new TestErr();
//        err.test();
//    }

    public void test(){
        Field[] fields = TestErr.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            System.out.println(type);
        }
    }
    @Override
    public void action(Exception e, Object obj) {
        System.err.println(obj+""+e.getMessage());
    }
}
