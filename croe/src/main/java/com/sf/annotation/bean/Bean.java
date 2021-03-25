package com.sf.annotation.bean;

import com.sf.annotation.croe.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
     /**
      * 创建bean的类型
      * @return
      */
     int type() default BeanType.single;

     /**
      * bean的参数
      * @return
      */
     Class<?>[] parameterType() default {};
}
