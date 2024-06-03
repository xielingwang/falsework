package com.wamogu.biz.sys.pojo;

import com.wamogu.querykit.FwQueryBase;
import com.wamogu.querykit.anno.FwQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
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
public final class SysConfigQuery extends FwQueryBase {
    @Schema(
            title = "id主键",
            description = "id主键"
    )
    @FwQuery
    private Integer id;

    @Schema(
            title = "key",
            description = "key"
    )
    @FwQuery
    private String key;

    @Schema(
            title = "设置值",
            description = "设置值"
    )
    @FwQuery
    private String value;

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
