package com.wamogu.biz.auth.pojo;

import com.wamogu.enums.UserIdentifyType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-13
 */
@Data
@Schema(
        title = "FwUserAuthMethodDto",
        description = "FwUserAuthMethodDto"
)
public final class FwUserAuthMethodDto {
    @Schema(
            title = "用户id",
            description = "用户id"
    )
    private Integer uid;

    @Schema(
            title = "验证方式",
            description = "验证方式"
    )
    private UserIdentifyType type;

    @Schema(
            title = "参数1",
            description = "参数1"
    )
    private String param1;

    @Schema(
            title = "参数2",
            description = "参数2"
    )
    private String param2;

    @Schema(
            title = "是否禁用",
            description = "是否禁用"
    )
    private boolean disabled;

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
