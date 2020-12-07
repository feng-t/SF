package com.sf1.asm;

import com.sf.annotation.EnableAOP;
import com.sf.annotation.beforeMethod;

@EnableAOP
public class Account {

    @beforeMethod
    public void operation(){
        System.out.println("正在执行 operation");
//        System.out.println("acc");
    }
}