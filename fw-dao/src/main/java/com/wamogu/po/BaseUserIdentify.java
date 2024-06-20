/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.po;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wamogu.enums.UserIdentifyType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Armin
 *
 * @date 24-06-12 00:41
 */
@Getter
@Setter
public abstract class BaseUserIdentify {

    // 用户验证类型
    protected UserIdentifyType type;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static BaseUserIdentify resolve(String json) {
        return null;
    }
}
