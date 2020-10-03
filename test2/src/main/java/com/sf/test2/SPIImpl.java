package com.sf.test2;


import com.sf.test1.SPITest;

public class SPIImpl implements SPITest {
    @Override
    public void test() {
        System.out.println("TEST 2");
    }

    @Override
    public String toString() {
        return "test spi";
    }
}
