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
import com.tangzc.mpe.autotable.annotation.UniqueIndex;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.MutableEntity;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 *
 * @date 24-06-12 17:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "AUTH-角色表")
@TableName(value = "auth_roles", autoResultMap = true)
@AutoDefine
@AutoRepository
public class Role extends MutableEntity {

    @Column(comment = "角色项(ROLE_开头)")
    @UniqueIndex
    private String roleKey;

    @Column(comment = "角色说明")
    private String roleRemark;

    @Column(comment = "权限项")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> privileges;
}
