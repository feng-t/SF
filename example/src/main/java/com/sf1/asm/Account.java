package com.sf1.asm;

import com.sf.annotation.EnableAOP;

@EnableAOP
public class Account {


    public void operation(){
        System.out.println("正在执行 operation");
//        System.out.println("acc");
    }

    public static void main(String[] args) {

    }
}