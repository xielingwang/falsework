/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.rest.base;

import com.wamogu.biz.auth.pojo.FwPrivilegeVo;
import com.wamogu.security.annotation.FwAnonymousAccess;
import com.wamogu.security.service.FwSecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Armin
 *
 * @date 24-05-22 23:49
 */
@Tag(name = "系统 Info")
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/base")
@RequiredArgsConstructor
public class InfoController {

    private final FwSecurityService fss;

    @Value("${maven.package-time:unknown}")
    String mavenPackageTime;

    @FwAnonymousAccess
    @GetMapping("/health")
    public String health() {
        return "health";
    }

    @FwAnonymousAccess
    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("packagedTime", mavenPackageTime);
    }

    @GetMapping("/logging")
    public String index() {
        log.trace("trace", new Exception());
        log.debug("debug", new Exception());
        log.info("info", new Exception());
        log.error("error", new Exception());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return "logging";
    }

    @FwAnonymousAccess
    @GetMapping("/scan-privileges")
    public List<FwPrivilegeVo> getAllPrivileges() {
        return fss.crudPrivileges();
    }
}
