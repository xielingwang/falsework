package com.wamogu.biz.gtd.pojo;

import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.anno.FwQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-02
 */
@Data
@EqualsAndHashCode(
        callSuper = true
)
public final class GtdTodoItemQuery extends FwQueryBase {
    @Schema(
            title = "id",
            description = "id"
    )
    @FwQuery
    private Integer id;

    @Schema(
            title = "用户id",
            description = "用户id"
    )
    @FwQuery
    private Long uid;

    @Schema(
            title = "内容",
            description = "内容"
    )
    @FwQuery
    private String content;

    @Schema(
            title = "备注",
            description = "备注"
    )
    @FwQuery
    private String remark;

    @Schema(
            title = "是否完成",
            description = "是否完成"
    )
    @FwQuery
    private Boolean isCommpleted;

    @Schema(
            title = "完成时间",
            description = "完成时间"
    )
    @FwQuery
    private LocalDateTime completedTime;

    @Schema(
            title = "创建人",
            description = "创建人"
    )
    @FwQuery
    private Serializable createBy;

    @Schema(
            title = "最后更新人",
            description = "最后更新人"
    )
    @FwQuery
    private Serializable updateBy;

    @Schema(
            title = "创建时间",
            description = "创建时间"
    )
    @FwQuery
    private Object createTime;

    @Schema(
            title = "最后更新时间",
            description = "最后更新时间"
    )
    @FwQuery
    private Object updateTime;
}
