package com.wamogu.biz.sys.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        title = "系统配置 VO",
        description = "系统配置 VO"
)
public final class SysConfigVo {
    @Schema(
            title = "id主键",
            description = "id主键"
    )
    private Integer id;

    @Schema(
            title = "key",
            description = "key"
    )
    private String key;

    @Schema(
            title = "设置值",
            description = "设置值"
    )
    private String value;

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