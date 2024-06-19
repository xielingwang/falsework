package com.wamogu.rest.base;

import com.wamogu.security.annotation.FwAnonymousAccess;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Armin
 * @date 24-05-22 23:49
 */
@Tag(name = "系统 Info")
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/base")
public class InfoController {
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
        return "logging";
    }
}
