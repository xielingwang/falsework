/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wamogu.enums.UserGenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author Armin
 *
 * @date 24-06-12 00:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProps {

    @Schema(title = "民族", description = "民族")
    private String nation;

    @Schema(title = "民族", description = "民族")
    @JsonFormat
    private UserGenderType gender;
}
