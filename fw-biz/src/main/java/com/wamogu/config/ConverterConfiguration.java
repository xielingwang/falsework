package com.wamogu.config;

import cn.hutool.core.date.DatePattern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author Armin
 * @date 24-06-17 21:35
 */
@Configuration
public class ConverterConfiguration {
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return source -> LocalTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        return source -> {
            SimpleDateFormat format = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            try {
                return format.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
