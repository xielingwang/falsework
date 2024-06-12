package com.wamogu.entity.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tangzc.autotable.annotation.ColumnType;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.MutableEntity;
import com.wamogu.po.BaseUserIdentify;
import com.wamogu.po.UserProps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @Author Armin
 * @date 24-06-12 17:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "AUTH-用户表")
@TableName(value = "auth_users", autoResultMap = true)
@AutoDefine
@AutoRepository
public class User extends MutableEntity {

    @Column(comment= "用户名")
    private String username;

    @Column(comment= "名")
    private String givenName;

    @Column(comment= "姓")
    private String familyName;

    @Column(comment= "昵称")
    private String nickName;

    @Column(comment= "密码")
    private String pwd;

    @Column(comment= "是否启用")
    private boolean enabled;

    @Column(comment= "用户属性")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private UserProps props;

    @Column(comment= "accesses")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> accesses;

    @Column(comment= "roles")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> roles;

    @Column(comment= "identifications")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<BaseUserIdentify> identifications;

    public void setProps(UserProps props) {
        this.props = props;
    }
}
