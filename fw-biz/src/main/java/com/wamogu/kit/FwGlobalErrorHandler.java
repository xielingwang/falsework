package com.wamogu.kit;

import com.feiniaojin.gracefulresponse.advice.GrGlobalExceptionAdvice;
import com.feiniaojin.gracefulresponse.api.ResponseFactory;
import com.feiniaojin.gracefulresponse.api.ResponseStatusFactory;
import com.feiniaojin.gracefulresponse.data.Response;
import com.feiniaojin.gracefulresponse.data.ResponseStatus;
import com.feiniaojin.gracefulresponse.defaults.DefaultResponseImplStyle0;
import com.feiniaojin.gracefulresponse.defaults.DefaultResponseStatus;
import jakarta.annotation.Resource;
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
public class FwGlobalErrorHandler {
    @Resource
    private GrGlobalExceptionAdvice grGlobalExceptionAdvice;

    @Resource
    private ResponseStatusFactory responseStatusFactory;

    @Resource
    private ResponseFactory responseFactory;

    @ExceptionHandler(BadCredentialsException.class)
    public Response handle(BadCredentialsException ex) {
        ResponseStatus rs = responseStatusFactory.newInstance("401", "错误的账号信息");
        return responseFactory.newInstance(rs);
    }
}
