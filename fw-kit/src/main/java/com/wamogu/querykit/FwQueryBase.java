/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.querykit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.Data;

/**
 * @Author Armin
 *
 * @date 24-05-29 17:14
 */
@Data
@Schema
public abstract class FwQueryBase {

    public static final Integer DEFAULT_PAGE_NUM = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 20;

    @Schema(description = "页码")
    protected Integer pageNum;

    @Schema(description = "每页数量")
    protected Integer pageSize;

    @Schema(description = "排序", example = "orderBy=xxx,yyyAsc,zzzDesc")
    protected String orderBy;

    public <E> Page<E> getPage(Class<E> clazz) {
        return new Page<E>()
                .setCurrent(Optional.of(pageNum).orElse(DEFAULT_PAGE_NUM))
                .setSize(Optional.of(pageSize).orElse(DEFAULT_PAGE_SIZE));
    }

    public static <E> Page<E> getDefaultPage(Class<E> clazz) {
        return new Page<E>().setCurrent(DEFAULT_PAGE_NUM).setSize(DEFAULT_PAGE_SIZE);
    }
}
