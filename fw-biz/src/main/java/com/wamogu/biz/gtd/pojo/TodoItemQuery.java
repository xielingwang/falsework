package com.wamogu.biz.gtd.pojo;

import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.FwQueryEnum;
import com.wamogu.querykit.anno.FwQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-05-29 01:42
 */
@Schema(title = "todo 查询")
@Data @EqualsAndHashCode(callSuper = true)
public final class TodoItemQuery extends FwQueryBase {
    @FwQuery(type = FwQueryEnum.LIKE)
    @Schema(description = "内容 d")
    String content;

    @FwQuery(type = FwQueryEnum.LIKE)
    @Schema(description = "备注")
    String remark;

    @FwQuery
    @Schema(description = "是否完成")
    Boolean isCommpleted;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "完成时间始")
    LocalDateTime completedTimeMin;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "完成时间末")
    LocalDateTime completedTimeMax;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "创建时间始")
    LocalDateTime createTimeMin;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "创建时间末")
    LocalDateTime createTimeMax;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "最后更新时间始")
    LocalDateTime updateTimeMin;

    @FwQuery(type = FwQueryEnum.BETWEEN)
    @Schema(description = "最后更新时间末")
    LocalDateTime updateTimeMax;
}