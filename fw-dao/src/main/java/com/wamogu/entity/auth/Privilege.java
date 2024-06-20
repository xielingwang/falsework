/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.entity.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.ImmutableEnity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 *
 * @date 24-06-12 17:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "AUTH-权限项表")
@TableName(value = "auth_privileges", autoResultMap = true)
@AutoDefine
@AutoRepository
public class Privilege extends ImmutableEnity {

    @Column(comment = "权限项")
    private String privilegeKey;

    @Column(comment = "权限项")
    private String privilegeGroup;

    @Column(comment = "权限说明")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String privilegeRemark;
}
