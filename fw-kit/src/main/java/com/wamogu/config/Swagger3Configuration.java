package com.wamogu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Armin
 * @date 24-05-28 22:14
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Falsework api 文档", version = "1.0", description = "Falsework api 文档 v1.0"))
public class Swagger3Configuration {

}

