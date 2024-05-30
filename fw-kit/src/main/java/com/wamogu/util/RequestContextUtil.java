package com.wamogu.util;

/**
 * @Author Armin
 * @date 24-05-28 21:55
 */

import com.wamogu.exception.ErrorKit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * spring上下文相关工具类
 *
 * @author ly-chn
 */
@Slf4j
public class RequestContextUtil {
    /**
     * @return 获取当前请求
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * @return 获取当前响应
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = Optional.ofNullable(RequestContextHolder.getRequestAttributes()).orElseThrow(() -> {
            log.error("非web上下文无法获取请求属性, 异步操作请在同步操作内获取所需信息");
            return new ErrorKit.Fatal("请求异常");
        });
        return ((ServletRequestAttributes) attributes);
    }
}
