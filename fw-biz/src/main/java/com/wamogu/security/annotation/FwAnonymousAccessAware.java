package com.wamogu.security.annotation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * @Author Armin
 * @date 24-06-19 18:25
 */
@Configuration
@Getter
public class FwAnonymousAccessAware implements InitializingBean, ApplicationContextAware {
    /**
     * 正则表达式 匹配path variable
     * 如： @GetMapping(value = "/configKey/{configKey}") 进行匹配后替换为/configKey/*
     *
     */
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    /**
     * spring 上下文
     * 从中可以获取到各种bean
     */
    private ApplicationContext applicationContext;
    /**
     * 该集合中保存了全部标记过匿名注解的url请求
     */
    private List<String> urlWhiteList = new ArrayList<>();

    public String ASTERISK = "*";

    @SafeVarargs
    public final <T> List<T> descartesProduct(@NotNull BiFunction<T, T, T> func, Collection<T>... lists) {
        List<T> resultList = new LinkedList<>();
        for (Collection<T> list : lists) {
            if (resultList.isEmpty()) {
                resultList.addAll(list);
            } else {
                resultList = resultList.stream().flatMap(x -> list.stream().map(y -> func.apply(x, y))).toList();
            }
        }
        return resultList;
    }

    @Override
    public void afterPropertiesSet()  {
        // 获取全部的handlerMappings
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info->{
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上的注解 替代path variable 为 *
            FwAnonymousAccess aaInMethod = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), FwAnonymousAccess.class);
            FwAnonymousAccess aaInController = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), FwAnonymousAccess.class);
            if (aaInMethod != null || aaInController != null){
                List<String> httpMethods = info.getMethodsCondition().getMethods().stream()
                        .map(String::valueOf).toList();
                List<String> reqPatterns = info.getPatternValues().stream()
                        .map(url -> RegExUtils.replaceAll(url, PATTERN, ASTERISK))
                        .toList();
                urlWhiteList.addAll(descartesProduct((s1, s2) -> s1+" "+s2, httpMethods, reqPatterns));
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
