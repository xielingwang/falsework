/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.biz.sys.service;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wamogu.biz.sys.convert.FwOptionCastor;
import com.wamogu.biz.sys.pojo.FwOptionDto;
import com.wamogu.biz.sys.pojo.FwOptionVo;
import com.wamogu.biz.sys.pojo.ISetting;
import com.wamogu.dao.repository.OptionRepository;
import com.wamogu.entity.sys.Option;
import com.wamogu.kit.BaseBizService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * generated by FwUtilCodegen
 *
 * @since 2024-06-12
 */
@Getter
@Service
@RequiredArgsConstructor
public final class FwOptionBizService extends BaseBizService<Option, FwOptionDto, FwOptionVo, Integer> {

    private final FwOptionCastor baseCastor;

    private final OptionRepository baseRepository;

    public <T extends ISetting> T getSetting(Class<T> clazz) {
        T dto = ReflectUtil.newInstance(clazz);
        List<Option> list = this.getBaseRepository()
                .lambdaQuery()
                .in(Option::getOptionKey, dto.fieldNames())
                .list();
        dto.fromOptions(list);
        return dto;
    }

    public <T extends ISetting> T setSetting(T dto) {
        dto.toOptions().forEach(x -> {
            LambdaQueryWrapper<Option> wrapper =
                    new LambdaQueryWrapper<Option>().eq(Option::getOptionKey, x.getOptionKey());
            boolean r = this.getBaseRepository().update(x, wrapper);
            if (!r) {
                this.getBaseRepository().save(x);
            }
        });
        return dto;
    }
}
