/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.feiniaojin.gracefulresponse.advice.GrGlobalExceptionAdvice;
import com.feiniaojin.gracefulresponse.data.Response;
import com.wamogu.exception.ErrorKit;
import com.wamogu.kit.FwJsonUtils;
import com.wamogu.security.constants.FwTokenType;
import com.wamogu.security.service.FwJwtKitService;
import com.wamogu.security.service.FwTokenStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Author Armin
 *
 * @date 24-06-13 11:45
 */
// @Component
@Slf4j
@RequiredArgsConstructor
public class FwJwtAuthFilter extends OncePerRequestFilter {

    /** 白名单路径 */
    public static final String[] WHITE_LIST_URL = {
        // springfox
        "/doc.html",
        "/webjars/js/**",
        "/webjars/css/**",
        "/v3/api-docs/**",
        "/gtd_todo_item/**",
        // auth
        "/auth/**",
        "/demo/**",
    };

    private final GrGlobalExceptionAdvice grGlobalExceptionAdvice;

    private final FwJwtKitService fwJwtKitService;

    private final UserDetailsService userDetailsService;

    private final FwTokenStorage fwTokenStorage;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith(FwTokenType.BEARER_ST)) {
                throw new ErrorKit.Offline();
            }

            // 检查 token 中的用户信息
            final String jwt = fwJwtKitService.extractTokenFromHeader(authHeader);
            final String username = fwJwtKitService.extractUsername(jwt);
            if (StrUtil.isBlank(username) || SecurityContextHolder.getContext().getAuthentication() != null) {
                throw new ErrorKit.Forbidden("token中用户信息缺失");
            }

            // 检查 token 有效性
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            var isTokenValid = fwTokenStorage
                    .findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (!fwJwtKitService.isTokenValid(jwt, userDetails) || !isTokenValid) {
                throw new ErrorKit.Forbidden("token已经失效或过期");
            }

            // 正常流程
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            Response resp = grGlobalExceptionAdvice.exceptionHandler(ex);
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            IoUtil.writeUtf8(response.getOutputStream(), true, FwJsonUtils.bean2json(resp));
        }
    }
}
