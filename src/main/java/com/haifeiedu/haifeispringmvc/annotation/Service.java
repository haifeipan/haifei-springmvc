package com.haifeiedu.haifeispringmvc.annotation;

import java.lang.annotation.*;

/**
 * Service Annotation, used to identify a service object and inject it into spring container.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
