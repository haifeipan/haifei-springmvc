package com.haifeiedu.haifeispringmvc.annotation;

import java.lang.annotation.*;

/**
 *
 * annotation is used to mark controllers/components
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
