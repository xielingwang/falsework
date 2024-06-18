package com.wamogu.security.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-06-14 17:12
 */
@Builder
@Data
public class FwTokenVo {
    private String token;
    private String refreshToken;
    private LocalDateTime currentTime;
}
