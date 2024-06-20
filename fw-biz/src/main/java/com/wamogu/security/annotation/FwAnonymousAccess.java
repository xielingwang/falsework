/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security.annotation;

import java.lang.annotation.*;

/**
 * @Author Armin
 *
 * @date 24-06-19 18:20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FwAnonymousAccess {}
