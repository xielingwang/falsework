package com.wamogu.biz.auth.pojo;

import com.wamogu.po.UserProps;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-20
 */
@Data
@Schema(
        title = "AUTH-用户表 VO",
        description = "AUTH-用户表 VO"
)
public final class FwUserVo {
    @Schema(
            title = "用户名",
            description = "用户名"
    )
    private String username;

    @Schema(
            title = "名",
            description = "名"
    )
    private String givenName;

    @Schema(
            title = "姓",
            description = "姓"
    )
    private String familyName;

    @Schema(
            title = "昵称",
            description = "昵称"
    )
    private String nickName;

    @Schema(
            title = "密码",
            description = "密码"
    )
    private String password;

    @Schema(
            title = "是否禁用",
            description = "是否禁用"
    )
    private boolean disabled;

    @Schema(
            title = "用户属性",
            description = "用户属性"
    )
    private UserProps props;

    @Schema(
            title = "权限项",
            description = "权限项"
    )
    private Set<String> privileges;

    @Schema(
            title = "角色",
            description = "角色"
    )
    private Set<String> roles;

    @Schema(
            title = "id",
            description = "id"
    )
    private Integer id;

    @Schema(
            title = "创建人",
            description = "创建人"
    )
    private Integer createBy;

    @Schema(
            title = "最后更新人",
            description = "最后更新人"
    )
    private Integer updateBy;

    @Schema(
            title = "创建时间",
            description = "创建时间"
    )
    private LocalDateTime createTime;

    @Schema(
            title = "最后更新时间",
            description = "最后更新时间"
    )
    private LocalDateTime updateTime;
}
