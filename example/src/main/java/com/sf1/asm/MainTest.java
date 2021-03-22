package com.sf1.asm;

public class MainTest {
    public static void main(String[] args) throws Exception {


        SecureAccountGenerator generator = new SecureAccountGenerator();
        int k = 2;
        Account a1 = (Account) generator.generateSecureAccount("com.sf1.asm.Account");
        printTime(() -> {
            for (int i = 0; i < k; i++) {
                a1.operation();
            }
        }, "1");

    }

    public static void printTime(A a, String name) throws Exception {
        long start = System.currentTimeMillis();
        a.apply();
        long end = System.currentTimeMillis();
        System.out.println(name + "执行了：" + (end - start) + ":ms");
    }

    interface A {
        void apply() throws Exception;
    }
}
