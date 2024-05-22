package com.wamogu.falsework.modules.base.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Armin
 * @date 24-05-22 23:49
 */
@RestController
@Slf4j
@ResponseBody
public class InfoController {
    @GetMapping("/health")
    public String health() {
        return "health";
    }

    @GetMapping("/info")
    public String info() {
        return "Hello World";
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
