package com.wamogu.biz.gtd.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-12
 */
@Data
@Schema(
        title = "FwGtdTodoItemDto",
        description = "FwGtdTodoItemDto"
)
public final class FwGtdTodoItemDto {
    @Schema(
            title = "id",
            description = "id"
    )
    private Integer id;

    @Schema(
            title = "用户id",
            description = "用户id"
    )
    private Long uid;

    @Schema(
            title = "内容",
            description = "内容"
    )
    @NotNull(
            message = "内容不为空"
    )
    private String content;

    @Schema(
            title = "备注",
            description = "备注"
    )
    private String remark;

    @Schema(
            title = "是否完成",
            description = "是否完成"
    )
    private Boolean isCommpleted;

    @Schema(
            title = "完成时间",
            description = "完成时间"
    )
    private LocalDateTime completedTime;

    @Schema(
            title = "创建人",
            description = "创建人"
    )
    private Serializable createBy;

    @Schema(
            title = "最后更新人",
            description = "最后更新人"
    )
    private Serializable updateBy;

    @Schema(
            title = "创建时间",
            description = "创建时间"
    )
    private Object createTime;

    @Schema(
            title = "最后更新时间",
            description = "最后更新时间"
    )
    private Object updateTime;
}