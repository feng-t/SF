package com.sf.exception;

public class NotFoundFactory extends RuntimeException {
    public NotFoundFactory(String msg) {
        super(msg);
    }
}
