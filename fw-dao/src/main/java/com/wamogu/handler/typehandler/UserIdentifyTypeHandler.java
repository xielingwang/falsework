package com.wamogu.handler.typehandler;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wamogu.enums.UserIdentifyType;
import com.wamogu.kit.FwJsonUtils;
import com.wamogu.po.BaseUserIdentify;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Field;

/**
 * @Author Armin
 * @date 24-06-12 14:10
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class UserIdentifyTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static ObjectMapper OBJECT_MAPPER;

    public UserIdentifyTypeHandler(Class<?> type) {
        super(type);
    }

    public UserIdentifyTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public Object parse(String json) {
        ObjectMapper objectMapper = getObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType javaType = typeFactory.constructType(getFieldType());
        try {
            Object obj = objectMapper.readValue(json, javaType);
            if (ObjectUtils.isNotNull(obj) && obj instanceof BaseUserIdentify) {
                return FwJsonUtils.json2Bean(json, UserIdentifyType.getTargetClass(((BaseUserIdentify) obj).getType()));
            }
            return obj;
        } catch (JacksonException e) {
            log.error("deserialize json: " + json + " to " + javaType + " error ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("serialize " + obj + " to json error ", e);
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getObjectMapper() {
        if (null == OBJECT_MAPPER) {
            OBJECT_MAPPER = new ObjectMapper();
        }
        return OBJECT_MAPPER;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper should not be null");
        OBJECT_MAPPER = objectMapper;
    }
}
