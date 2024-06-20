/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.enums.DefaultValueEnum;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.MutableEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @Author Armin
 *
 * @date 24-06-02 01:21
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
@AutoDefine
@AutoRepository
@Table(comment = "系统配置")
@TableName(value = "sys_options", autoResultMap = true)
public class Option extends MutableEntity {

    @NotNull @Column(value = "`key`", length = 100, notNull = true, comment = "key")
    private String optionKey;

    @Column(value = "`value`", length = 1024, comment = "设置值", defaultValueType = DefaultValueEnum.EMPTY_STRING)
    private String optionValue;

    @ColumnComment("自动加载")
    private String autoload;
}
