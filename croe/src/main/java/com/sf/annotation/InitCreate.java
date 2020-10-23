package com.sf.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitCreate {
    Class<?>[] parameters()  default {};
}
