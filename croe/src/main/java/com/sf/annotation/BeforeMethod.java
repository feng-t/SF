package com.sf.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BeforeMethod {


}
