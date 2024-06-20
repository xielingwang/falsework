/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author Armin
 *
 * @date 24-06-12 11:36
 */
public interface IEnumerator extends IEnum<String> {

    String getDescription();
}
