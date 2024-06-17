package com.wamogu.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoDefine;
import com.tangzc.mpe.processer.annotation.AutoRepository;
import com.wamogu.entity.MutableEntity;
import com.wamogu.enums.UserGenderType;
import com.wamogu.enums.UserIdentifyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Armin
 * @date 24-06-13 00:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "AUTH-用户验证方式表")
@TableName(value = "auth_userauthes", autoResultMap = true)
@AutoDefine
@AutoRepository
public class UserAuthMethod extends MutableEntity {

    @Column(value = "`uid`", comment = "用户id")
    private Integer uid;
    @Column(value = "`type`", comment = "验证方式")
    @JsonFormat
    private UserIdentifyType type;
    @Column(comment = "参数1")
    private String param1;
    @Column(comment = "参数2")
    private String param2;
    @Column(comment = "是否禁用")
    private boolean disabled = false;
}
