/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.auth.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Data
@Schema(title = "AUTH-权限项表 VO", description = "AUTH-权限项表 VO")
public final class FwPrivilegeVo {

    @Schema(title = "权限项", description = "权限项")
    private String privilegeKey;

    @Schema(title = "权限项", description = "权限项")
    private String privilegeGroup;

    @Schema(title = "权限说明", description = "权限说明")
    private String privilegeRemark;

    @Schema(title = "id", description = "id")
    private Integer id;

    @Schema(title = "创建人", description = "创建人")
    private Integer createBy;

    @Schema(title = "创建时间", description = "创建时间")
    private LocalDateTime createTime;
}