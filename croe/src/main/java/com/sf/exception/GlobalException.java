package com.sf.exception;

/**
 * 全局异常处理
 */
public interface GlobalException<T extends Exception>{
    default void process(T t){
        t.printStackTrace();
    }
}
