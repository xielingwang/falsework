package com.wamogu.querykit.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构建新增/修改时忽略该字段
 * @Author Armin
 * @date 24-05-28 22:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MbpEditIgnore {
}
