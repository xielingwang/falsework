/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * @Author Armin
 *
 * @date 24-06-12 11:31
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserGenderType implements IEnumerator {
    MALE("1", "男性"),
    FEMALE("0", "女性"),
    ;

    @EnumValue
    @JsonValue
    private final String value;

    private final String description;

    UserGenderType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    private static final Map<String, UserGenderType> map =
            Arrays.stream(values()).collect(Collectors.toMap(UserGenderType::getValue, x -> x));

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static UserGenderType resolve(String value) {
        if (!map.containsKey(value)) {
            throw new RuntimeException(String.format("%s 枚举中未存在 %s 值", UserGenderType.class.getName(), value));
        }
        return map.get(value);
    }
}
