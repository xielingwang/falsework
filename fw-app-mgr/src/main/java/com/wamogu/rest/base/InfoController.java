package com.wamogu.rest.base;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
