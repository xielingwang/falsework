/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.po;

import com.wamogu.entity.auth.UserAuthMethod;
import lombok.*;

/**
 * @Author Armin
 *
 * @date 24-06-12 11:54
 */
@EqualsAndHashCode(callSuper = true)
public class UserAuthEmail extends UserAuthMethod {

    public String getEmail() {
        return getParam1();
    }

    public Boolean getVerified() {
        return Boolean.getBoolean(getParam2());
    }

    public void setVerified(Boolean b) {
        setParam2(Boolean.toString(Boolean.TRUE.equals(b)));
    }
}
