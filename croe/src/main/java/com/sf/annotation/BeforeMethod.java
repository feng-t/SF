package com.sf.annotation;

import com.sf.annotation.apply.Component;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BeforeMethod {


}
