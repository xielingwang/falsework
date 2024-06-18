package com.wamogu.rest.base;

import com.wamogu.exception.ErrorKit;
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
@Tag(name = "系统 Demo")
@RestController
@Slf4j
@ResponseBody
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/ex/fatal")
    public String ex() {
        throw new ErrorKit.Fatal("Fatal");
    }
}