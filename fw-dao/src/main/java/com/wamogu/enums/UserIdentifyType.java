/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wamogu.po.BaseUserIdentify;
import com.wamogu.po.UserAuthPhone;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * @Author Armin
 *
 * @date 24-06-12 00:48
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserIdentifyType implements IEnumerator {
    PHONE_VERIFY("pp", "手机号验证", UserAuthPhone.class),
    EMAIL_VERIFY("ep", "邮箱验证", UserAuthPhone.class),
// WX_LOGIN("wx","微信登录", UserAuthPhone.class),
// WEIBO_LOGIN("wb","微信登录", UserAuthPhone.class),
;

    @EnumValue
    @JsonValue
    private final String value;

    private final String description;

    private final Class<?> targetClass;

    UserIdentifyType(String value, String description, Class<?> clazz) {
        this.value = value;
        this.description = description;
        this.targetClass = clazz;
    }

    public static <T extends BaseUserIdentify> Class<T> getTargetClass(IEnumerator enumerator) {
        for (UserIdentifyType t : UserIdentifyType.values()) {
            if (t.getValue().equalsIgnoreCase(enumerator.getValue())) {
                return (Class<T>) t.getTargetClass();
            }
        }
        return null;
    }

    private static final Map<String, UserIdentifyType> map =
            Arrays.stream(values()).collect(Collectors.toMap(UserIdentifyType::getValue, x -> x));

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static UserIdentifyType resolve(String value) {
        if (!map.containsKey(value)) {
            throw new RuntimeException(String.format("%s 枚举中未存在 %s 值", UserIdentifyType.class.getName(), value));
        }
        return map.get(value);
    }
}
