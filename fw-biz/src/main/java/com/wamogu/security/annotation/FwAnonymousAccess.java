package com.wamogu.security.annotation;

import java.lang.annotation.*;

/**
 * @Author Armin
 * @date 24-06-19 18:20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FwAnonymousAccess {
}
