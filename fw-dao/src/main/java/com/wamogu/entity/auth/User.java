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
import com.wamogu.po.UserProps;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 *
 * @date 24-06-12 17:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "AUTH-用户表")
@TableName(value = "auth_users", autoResultMap = true)
@AutoDefine
@AutoRepository
public class User extends MutableEntity {

    @NotBlank
    @UniqueIndex
    @Column(comment = "用户名")
    private String username;

    @Column(comment = "名")
    private String givenName;

    @Column(comment = "姓")
    private String familyName;

    @Column(comment = "昵称")
    private String nickName;

    @Column(comment = "密码")
    private String password;

    @Column(comment = "是否禁用")
    private boolean disabled;

    @Column(comment = "用户属性")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private UserProps props;

    @Column(comment = "权限项")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> privileges;

    @Column(comment = "角色")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> roles;
}
