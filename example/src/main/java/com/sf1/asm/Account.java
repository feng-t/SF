package com.sf1.asm;

import com.sf.annotation.Bean;

import java.lang.reflect.Constructor;

public class Account {


    @Bean
    public void operation(){
        System.out.println("正在执行 operation");
//        System.out.println("acc");
    }

    public void operation(String str){
        System.out.println("str print");
    }


}