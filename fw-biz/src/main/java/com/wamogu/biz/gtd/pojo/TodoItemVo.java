package com.wamogu.biz.gtd.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-05-29 12:41
 */
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "todo 对象")
public class TodoItemVo {
    @Schema(title = "id")
    private Integer id;

    @Schema(title = "用户id")
    private Long uid;

    @Schema(title = "内容")
    private String content;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "是否完成")
    private Boolean isCommpleted;

    @Schema(title = "完成时间")
    private LocalDateTime completedTime;

    @Schema(title = "创建人")
    private Integer createBy;

    @Schema(title = "最后更新人")
    private Integer updateBy;

    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    @Schema(title = "最后更新时间")
    private LocalDateTime updateTime;
}
