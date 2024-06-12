package com.wamogu.biz.auth.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-12
 */
@Data
@Schema(
        title = "FwRoleDto",
        description = "FwRoleDto"
)
public final class FwRoleDto {
    @Schema(
            title = "角色项",
            description = "角色项"
    )
    private String roleKey;

    @Schema(
            title = "角色说明",
            description = "角色说明"
    )
    private String roleRemark;

    @Schema(
            title = "accesses",
            description = "accesses"
    )
    private Set accesses;

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
