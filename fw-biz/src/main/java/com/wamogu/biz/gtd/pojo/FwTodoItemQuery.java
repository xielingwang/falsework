/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.gtd.pojo;

import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.anno.FwQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class FwTodoItemQuery extends FwQueryBase {

    @Schema(title = "用户id", description = "用户id")
    @FwQuery
    private Long uid;

    @Schema(title = "content", description = "content")
    @FwQuery
    private String content;

    @Schema(title = "备注", description = "备注")
    @FwQuery
    private String remark;

    @Schema(title = "isCompleted", description = "isCompleted")
    @FwQuery
    private Boolean isCompleted;

    @Schema(title = "完成时间", description = "完成时间")
    @FwQuery
    private LocalDateTime completedTime;

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
