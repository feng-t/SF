package com.sf1.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class MainTest {
    public static void main(String[] args) throws Exception {

//        (T)add: 将栈顶 T 类型的两个数值相加后入栈，T：float,int,short,long,double
//        (T)sub: 将栈顶 T 类型的两个数值相减后入栈，T：float,int,short,long,double
//        (T)mul: 将栈顶 T 类型的两个数值相乘后入栈，T：float,int,short,long,double
//        (T)div: 将栈顶 T 类型的两个数值相除后入栈，T：float,int,short,long,double
//        (T)rem: 将栈顶 T 类型的两个数值取模后入栈，T：float,int,short,long,double
//        (T)neg: 将栈顶 T 类型的取负后入栈，T：float,int,short,long,double
//        (T)iinc [indexbyte,constantbyte]: 将整数值 constbyte 加到 indexbyte 指定的 int 类型的局部变量中;

//        System.out.println(Account.class.getName());
        SecureAccountGenerator generator = new SecureAccountGenerator();
//        Account account = (Account) generator.generateSecureAccount();
//        account.operation();

        int k=999999999;

        printTime(()->{
            Account a1 = (Account) generator.generateSecureAccount();
            for (int i = 0; i < k; i++) {
                a1.operation();
            }
        });

        printTime(()->{
            Account a2 = new Account();
            for (int i = 0; i < k; i++) {
                a2.operation();
            }
        });
        Class<?> aClass = Class.forName("com.sf1.asm.Account");

        printTime(()->{
            Account o = (Account) aClass.newInstance();
            for (int i = 0; i < k; i++) {
                o.operation();
            }
        });

//        Account account = new Account();
//        account.operation();

    }

    public static void printTime(A a) throws Exception {
        long start = System.currentTimeMillis();
        a.apply();
        long end = System.currentTimeMillis();
        System.out.println("执行了："+(end-start)+":ms");
    }
    interface A{
        void apply() throws Exception;
    }
}
