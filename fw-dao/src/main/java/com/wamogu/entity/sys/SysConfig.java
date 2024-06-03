package com.wamogu.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.ColumnDefault;
import com.tangzc.autotable.annotation.ColumnType;
import com.tangzc.autotable.annotation.enums.DefaultValueEnum;
import com.tangzc.autotable.annotation.mysql.MysqlTypeConstant;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.base.entity.BaseEntity;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-06-02 01:21
 */
@Data @Builder @EqualsAndHashCode(callSuper=false)
@AutoDefine @AutoRepository
@Table(value = "sys_config", comment = "系统配置")
public class SysConfig extends BaseEntity<Integer, LocalDateTime> {
    @ColumnComment("id主键")
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Integer id;

    @NotNull
    @ColumnDefault(type = DefaultValueEnum.EMPTY_STRING)
    @ColumnType(length = 100)
    @ColumnComment("key")
    private String key;

    @ColumnDefault("")
    @ColumnType(length = 1024)
    @ColumnComment("设置值")
    private String value;
}
