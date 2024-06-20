/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.gtd.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Data
@Schema(title = "FwTodoItemDto", description = "FwTodoItemDto")
public final class FwTodoItemDto {

    @Schema(title = "用户id", description = "用户id")
    private Long uid;

    @Schema(title = "content", description = "content")
    @NotNull(message = "内容不为空") private String content;

    @Schema(title = "备注", description = "备注")
    private String remark;

    @Schema(title = "isCompleted", description = "isCompleted")
    private Boolean isCompleted;

    @Schema(title = "完成时间", description = "完成时间")
    private LocalDateTime completedTime;

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