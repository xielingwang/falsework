/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.kit;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Armin
 *
 * @date 24-06-04 09:37
 */
@Slf4j
@UtilityClass
public class FwJsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 设置时区
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 日期类型格式
        mapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
        // 序列化所有参数，包括null
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public <T> String bean2json(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T json2Bean(String jsonStr, Class<T> objClass) {
        if (CharSequenceUtil.isBlank(jsonStr)) {
            return null;
        }
        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> json2list(String jsonStr, Class<T> objClass) {
        if (CharSequenceUtil.isBlank(jsonStr)) {
            return null;
        }
        try {
            return mapper.readerForListOf(objClass).readValue(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
