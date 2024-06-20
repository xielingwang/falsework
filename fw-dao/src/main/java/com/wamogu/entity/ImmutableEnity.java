/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.mpe.annotation.InsertFillData;
import com.tangzc.mpe.annotation.InsertFillTime;
import com.tangzc.mpe.annotation.handler.IOptionByAutoFillHandler;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Armin
 *
 * @date 24-06-12 00:32
 */
@Getter
@Setter
public class ImmutableEnity {

    @ColumnId(value = "id", comment = "id", mode = IdType.AUTO)
    protected Integer id;

    @InsertFillData(IOptionByAutoFillHandler.class)
    @ColumnComment("创建人")
    protected Integer createBy;

    @InsertFillTime
    @ColumnComment("创建时间")
    protected LocalDateTime createTime;
}
