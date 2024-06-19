package com.wamogu.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author Armin
 * @date 24-06-20 01:21
 */
@Configuration
public class DateConfig {
    interface String2LocalDateConverter extends Converter<String, LocalDate> {}
    interface String2LocalTimeConverter extends Converter<String, LocalTime> {}
    interface String2LocalDateTimeConverter extends Converter<String, LocalDateTime> {}
    interface String2DateConverter extends Converter<String, Date> {}
    @Bean
    public String2LocalDateConverter string2LocalDateConverter() {
        return source -> LocalDate.parse(
                source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }
    @Bean
    public String2LocalTimeConverter string2LocalTimeConverter() {
        return source -> LocalTime.parse(
                source, DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
    }

    @Bean
    public String2LocalDateTimeConverter string2LocalDateTimeConverter() {
        return source -> LocalDateTime.parse(
                source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }
    @Bean
    public String2DateConverter string2DateConverter() {
        return source -> DateUtil.parse(source, DatePattern.NORM_DATETIME_FORMAT);
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }

    @Bean
    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

    @Bean
    public LocalTimeSerializer localTimeSerializer() {
        return new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return e -> {
            e.serializerByType(LocalDateTime.class, DateConfig.this.localDateTimeSerializer());
            e.serializerByType(LocalDate.class, DateConfig.this.localDateSerializer());
            e.serializerByType(LocalTime.class, DateConfig.this.localTimeSerializer());
        };
    }
}
