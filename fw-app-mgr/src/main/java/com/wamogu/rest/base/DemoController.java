/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wamogu.exception.ErrorKit;
import com.wamogu.security.annotation.FwAnonymousAccess;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Armin
 *
 * @date 24-05-22 23:49
 */
@Tag(name = "系统 Demo")
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/demo")
@FwAnonymousAccess
public class DemoController {

    @GetMapping({"/ex/fatal", "/fatal-exception"})
    public String ex() {
        throw new ErrorKit.Fatal("Fatal Message");
    }

    @GetMapping({"/page", "/pg"})
    public Page<String> page() {
        return new Page<>(1, 2);
    }
}
