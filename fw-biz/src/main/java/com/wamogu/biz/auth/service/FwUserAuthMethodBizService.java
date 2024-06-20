/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.auth.service;

import com.wamogu.biz.auth.convert.FwUserAuthMethodCastor;
import com.wamogu.biz.auth.pojo.FwUserAuthMethodDto;
import com.wamogu.biz.auth.pojo.FwUserAuthMethodVo;
import com.wamogu.dao.repository.UserAuthMethodRepository;
import com.wamogu.entity.auth.UserAuthMethod;
import com.wamogu.kit.BaseBizService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-13
 */
@Getter
@Service
@RequiredArgsConstructor
public final class FwUserAuthMethodBizService
        extends BaseBizService<UserAuthMethod, FwUserAuthMethodDto, FwUserAuthMethodVo, Integer> {

    private final FwUserAuthMethodCastor baseCastor;

    private final UserAuthMethodRepository baseRepository;
}