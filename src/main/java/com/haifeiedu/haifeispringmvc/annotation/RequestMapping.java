package com.haifeiedu.haifeispringmvc.annotation;

import java.lang.annotation.*;

/**
 *
 * RequestMapping annotation is used to specify the mapping path of controller's method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
