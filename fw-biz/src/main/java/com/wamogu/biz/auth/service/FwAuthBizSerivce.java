package com.wamogu.biz.auth.service;

import cn.hutool.core.util.StrUtil;
import com.wamogu.biz.auth.convert.FwUserCastor;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.entity.auth.User;
import com.wamogu.security.model.FwPwdLoginQuery;
import com.wamogu.security.model.FwPwdRegQuery;
import com.wamogu.security.model.FwTokenVo;
import com.wamogu.security.model.FwUserDetails;
import com.wamogu.security.service.FwJwtKitService;
import com.wamogu.security.service.FwTokenStorage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author Armin
 * @date 24-06-14 15:05
 */
@Getter
@Service
@RequiredArgsConstructor
public class FwAuthBizSerivce {
    private final FwUserCastor fwUserCastor;
    private final FwUserBizService fwUserBizService;
    private final FwJwtKitService fwJwtKitService;
    private final FwTokenStorage fwTokenStorage;

    private final PasswordEncoder passwordEncoder; // 密码加密器
    private final AuthenticationManager authenticationManager; // Spring Security 认证管理器

    public FwTokenVo pwdRegister(FwPwdRegQuery query) {
        User user = new User();
        BeanUtils.copyProperties(query, user);;

        fwUserBizService.getBaseRepository().save(user);
        FwUserDto userDto = fwUserCastor.do2dto(user);

        return fwJwtKitService.generateTokenForLogin(userDto);
    }

    public FwTokenVo pwdLogin(FwPwdLoginQuery query) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(query.getUsername(), query.getPassword()));

        FwUserDto userDto = fwUserBizService.findByAvailableUsername(query.getUsername())
                .orElseThrow();

        return fwJwtKitService.generateTokenForLogin(userDto);
    }

    public FwTokenVo refreshToken(String param) {
        // 从鉴权信息中获取RefreshToken
        final String refreshToken = fwJwtKitService.extractTokenFromHeader(param);
        if(StrUtil.isBlank(refreshToken)) {
            return null;
        }
        // 从RefreshToken中获取用户信息
        final String username = fwJwtKitService.extractUsername(refreshToken);
        if (username == null) {
            return null;
        }

        // 根据用户信息查询用户,如果用户不存在抛出异常
        FwUserDto userDto = fwUserBizService.findByAvailableUsername(username).orElseThrow();
        return fwJwtKitService.generateTokenForRefresh(userDto, refreshToken);
    }

    public void logout(String authHeader) {
        // 从鉴权信息中获取RefreshToken
        final String token = fwJwtKitService.extractTokenFromHeader(authHeader);
        if(StrUtil.isBlank(token)) {
            return;
        }
        fwTokenStorage.expire(token);
    }
}
