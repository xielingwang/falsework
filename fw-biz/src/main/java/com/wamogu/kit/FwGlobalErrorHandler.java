package com.wamogu.kit;

import com.feiniaojin.gracefulresponse.advice.GrGlobalExceptionAdvice;
import com.feiniaojin.gracefulresponse.api.ResponseFactory;
import com.feiniaojin.gracefulresponse.api.ResponseStatusFactory;
import com.feiniaojin.gracefulresponse.data.Response;
import com.feiniaojin.gracefulresponse.data.ResponseStatus;
import com.feiniaojin.gracefulresponse.defaults.DefaultResponseImplStyle0;
import com.feiniaojin.gracefulresponse.defaults.DefaultResponseStatus;
import com.wamogu.exception.ErrorKit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Armin
 * @date 24-06-18 14:29
 */
@ControllerAdvice
@RestControllerAdvice
@Order(100)
@Slf4j
public class FwGlobalErrorHandler {
    @Resource
    private GrGlobalExceptionAdvice grGlobalExceptionAdvice;

    @Resource
    private ResponseStatusFactory responseStatusFactory;

    @Resource
    private ResponseFactory responseFactory;

    @ExceptionHandler(BadCredentialsException.class)
    public Response handle(BadCredentialsException ex) {
        ResponseStatus rs = responseStatusFactory.newInstance(ErrorKit.CODE_FORBIDDEN, "错误的账号信息");
        return responseFactory.newInstance(rs);
    }
    @ExceptionHandler(RuntimeException.class)
    public Response handle(RuntimeException ex) {
        log.error("异常错误", ex);
        ResponseStatus rs = responseStatusFactory.newInstance(ErrorKit.CODE_FATAL, "异常错误");
        return responseFactory.newInstance(rs);
    }
}
