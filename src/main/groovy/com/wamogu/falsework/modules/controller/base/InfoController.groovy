package com.wamogu.falsework.modules.controller.base

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@Tag(name = "Info")
class InfoController {

    @Operation(summary = "info 请求")
    @GetMapping("/info")
    @ResponseBody
    info() {
        var info = new Info()
        info.setName("falsework")
        info.setVersion("1.0.0")
        info
    }

    static class Info {
        String name
        String version
    }
}
