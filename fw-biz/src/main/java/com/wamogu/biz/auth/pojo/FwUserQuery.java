/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.auth.pojo;

import com.wamogu.po.UserProps;
import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.anno.FwQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class FwUserQuery extends FwQueryBase {

    @Schema(title = "用户名", description = "用户名")
    @FwQuery
    private String username;

    @Schema(title = "名", description = "名")
    @FwQuery
    private String givenName;

    @Schema(title = "姓", description = "姓")
    @FwQuery
    private String familyName;

    @Schema(title = "昵称", description = "昵称")
    @FwQuery
    private String nickName;

    @Schema(title = "密码", description = "密码")
    @FwQuery
    private String password;

    @Schema(title = "是否禁用", description = "是否禁用")
    @FwQuery
    private boolean disabled;

    @Schema(title = "用户属性", description = "用户属性")
    @FwQuery
    private UserProps props;

    @Schema(title = "权限项", description = "权限项")
    @FwQuery
    private Set<String> privileges;

    @Schema(title = "角色", description = "角色")
    @FwQuery
    private Set<String> roles;

    @Schema(title = "id", description = "id")
    @FwQuery
    private Integer id;

    @Schema(title = "创建人", description = "创建人")
    @FwQuery
    private Integer createBy;

    @Schema(title = "最后更新人", description = "最后更新人")
    @FwQuery
    private Integer updateBy;

    @Schema(title = "创建时间", description = "创建时间")
    @FwQuery
    private LocalDateTime createTime;

    @Schema(title = "最后更新时间", description = "最后更新时间")
    @FwQuery
    private LocalDateTime updateTime;
}
