package com.sf.exception;

/**
 * 全局异常处理
 */
public interface GlobalException{
    /**
     * 异常处理
     * @param e 异常
     */
    default void processException(Exception e){
        e.printStackTrace();
    };

//    default <T extends Exception> boolean is(T t){
//
//    }
}
