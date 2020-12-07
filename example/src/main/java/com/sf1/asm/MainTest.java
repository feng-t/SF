package com.sf1.asm;

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

        int k=2;

        Account a1 = (Account) generator.generateSecureAccount("com.sf1.asm.Account");

//        Thread.currentThread().getContextClassLoader()
        printTime(()->{
            for (int i = 0; i < k; i++) {
                a1.operation();
            }
        },"1");

        Account a2 = new Account();
        printTime(()->{
            for (int i = 0; i < k; i++) {
                a2.operation();
            }
        },"2");
        Class<?> aClass = Class.forName("com.sf1.asm.Account");
        Account o = (Account) aClass.newInstance();
        printTime(()->{
            for (int i = 0; i < k; i++) {
                o.operation();
            }
        },"3");

//        Account account = new Account();
//        account.operation();

    }

    public static void printTime(A a,String name) throws Exception {
        long start = System.currentTimeMillis();
        a.apply();
        long end = System.currentTimeMillis();
        System.out.println(name+"执行了："+(end-start)+":ms");
    }
    interface A{
        void apply() throws Exception;
    }
}
