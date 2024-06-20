/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.kit;

import com.feiniaojin.gracefulresponse.advice.GrGlobalExceptionAdvice;
import com.feiniaojin.gracefulresponse.api.ResponseFactory;
import com.feiniaojin.gracefulresponse.api.ResponseStatusFactory;
import com.feiniaojin.gracefulresponse.data.Response;
import com.feiniaojin.gracefulresponse.data.ResponseStatus;
import com.wamogu.exception.ErrorKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Armin
 *
 * @date 24-06-18 14:29
 */
@ControllerAdvice
@RestControllerAdvice
@Order(100)
@Slf4j
@RequiredArgsConstructor
public class FwGlobalErrorHandler {

    private final GrGlobalExceptionAdvice grGlobalExceptionAdvice;

    private final ResponseStatusFactory responseStatusFactory;

    private final ResponseFactory responseFactory;

    @ExceptionHandler(BadCredentialsException.class)
    public Response handle(BadCredentialsException ex) {
        ResponseStatus rs = responseStatusFactory.newInstance(ErrorKit.CODE_FORBIDDEN, "错误的账号信息");
        return responseFactory.newInstance(rs);
    }

    @ExceptionHandler(Throwable.class)
    public Response handle(Throwable ex) {
        log.error("异常错误", ex);
        return grGlobalExceptionAdvice.exceptionHandler(ex);
    }
}
