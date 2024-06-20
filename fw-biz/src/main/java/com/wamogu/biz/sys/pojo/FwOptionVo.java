/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.sys.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Data
@Schema(title = "系统配置 VO", description = "系统配置 VO")
public final class FwOptionVo {

    @Schema(title = "key", description = "key")
    private String optionKey;

    @Schema(title = "设置值", description = "设置值")
    private String optionValue;

    @Schema(title = "自动加载", description = "自动加载")
    private String autoload;

    @Schema(title = "id", description = "id")
    private Integer id;

    @Schema(title = "创建人", description = "创建人")
    private Integer createBy;

    @Schema(title = "最后更新人", description = "最后更新人")
    private Integer updateBy;

    @Schema(title = "创建时间", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(title = "最后更新时间", description = "最后更新时间")
    private LocalDateTime updateTime;
}
