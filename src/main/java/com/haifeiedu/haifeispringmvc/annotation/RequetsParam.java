package com.haifeiedu.haifeispringmvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequetsParam {
    String value() default "";
}
