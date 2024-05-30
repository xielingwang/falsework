package com.wamogu.biz.gtd.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * @Author Armin
 * @date 24-05-29 10:12
 */
@Schema(title = "todo 对象")
public record TodoItemDto (
    @NotNull(message = "内容不为空")
    @Schema(title = "内容")
    String content,

    @Schema(title = "备注")
    String remark,

    @Schema(title = "是否完成")
    Boolean isCommpleted
) { }
