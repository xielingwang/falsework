/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.auth.convert;

import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.biz.auth.pojo.FwUserVo;
import com.wamogu.entity.auth.User;
import com.wamogu.kit.BaseCastor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-20
 */
@Mapper(
        componentModel = "spring",
        uses = {},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FwUserCastor extends BaseCastor<User, FwUserDto, FwUserVo> {}
