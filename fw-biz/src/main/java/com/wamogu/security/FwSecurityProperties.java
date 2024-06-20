/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Armin
 *
 * @date 24-06-19 19:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "fwapp.security")
public class FwSecurityProperties {

    private List<String> urlWhiteList;

    private JwtSetting jwt;

    @Data
    public static class JwtSetting {

        String secretKey;

        Long tokenLifeMinutes;

        Long refreshTokenLifeMinutes;
    }
}
