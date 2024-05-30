package com.wamogu.querykit.anno;

import com.wamogu.querykit.FwQueryEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Armin
 * @date 24-05-28 21:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FwQuery {
    FwQueryEnum type() default FwQueryEnum.EQ;
}
