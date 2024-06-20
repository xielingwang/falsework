package com.wamogu.rest.base;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.tangzc.mpe.autotable.annotation.Table;
import com.wamogu.biz.auth.pojo.FwPrivilegeVo;
import com.wamogu.security.annotation.FwAnonymousAccess;
import com.wamogu.security.service.FwSecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * @Author Armin
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

    @FwAnonymousAccess
    @GetMapping("/health")
    public String health() {
        return "health";
    }

    @FwAnonymousAccess
    @GetMapping("/info/{str}")
    public String info(@PathVariable("str") String str) {
        return String.format("Hello %s!", str);
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
