package com.wamogu.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Armin
 * @date 24-06-19 19:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "fwapp.security")
public class FwSecuritySetting {
    private List<String> urlWhiteList;
    private JwtSetting jwt;

    @Data
    public static class JwtSetting {
        String secretKey;
        Long tokenLifeMinutes;
        Long refreshTokenLifeMinutes;
    }
}
