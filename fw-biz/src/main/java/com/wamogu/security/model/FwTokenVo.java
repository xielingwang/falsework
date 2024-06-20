/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * @Author Armin
 *
 * @date 24-06-14 17:12
 */
@Builder
@Data
public class FwTokenVo {

    private String token;

    private String refreshToken;

    private LocalDateTime currentTime;
}
