package com.sf.core.annotation;

import java.lang.annotation.*;

@Repeatable(Services.class)
public @interface Service {
    int hour() default 0;
}
