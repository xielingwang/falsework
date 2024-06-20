/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security.service;

import com.wamogu.security.constants.FwTokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * @Author Armin
 *
 * @date 24-06-18 11:24
 */
@Service
@RequiredArgsConstructor
public class FwLogoutService implements LogoutHandler {

    private final FwTokenStorage fwTokenStorage;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(FwTokenType.BEARER_ST)) {
            return;
        }
        jwt = authHeader.substring(FwTokenType.BEARER_ST.length());
        var storedToken = fwTokenStorage.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            fwTokenStorage.expire(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
