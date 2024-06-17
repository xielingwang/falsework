package com.wamogu.biz.auth.service;

import cn.hutool.core.util.StrUtil;
import com.wamogu.biz.auth.convert.FwUserCastor;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.entity.auth.User;
import com.wamogu.security.constants.FwTokenType;
import com.wamogu.security.model.FwPwdLoginQuery;
import com.wamogu.security.model.FwPwdRegQuery;
import com.wamogu.security.model.FwTokenVo;
import com.wamogu.security.model.FwUserDetails;
import com.wamogu.security.service.FwTokenStorage;
import com.wamogu.security.service.FwJwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final FwJwtUtil fwJwtUtil;
    private final FwTokenStorage fwTokenStorage;

    private final PasswordEncoder passwordEncoder; //密码加密器
    private final AuthenticationManager authenticationManager; //Spring Security 认证管理器

    public FwTokenVo pwdRegister(FwPwdRegQuery query) {
        User user = new User();
        BeanUtils.copyProperties(query, user);;

        fwUserBizService.getBaseRepository().save(user);
        FwUserDto userDto = fwUserCastor.do2dto(user);

        return generateNewToken(userDto, null);
    }

    public FwTokenVo pwdLogin(FwPwdLoginQuery query) {

        Authentication authorization = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(query.getUsername(), query.getPassword()));

        FwUserDto userDto = fwUserBizService.findByAvailableUsername(query.getUsername())
                .orElseThrow();

        return generateNewToken(userDto, null);
    }

    private FwTokenVo generateNewToken(FwUserDto userDto, String refreshToken) {
        FwUserDetails userDetails = FwUserDetails.valueOf(userDto, fwUserBizService.getAuthorities(userDto));
        if (StrUtil.isNotBlank(refreshToken)) {
            //验证Token是否有效
            if (!fwJwtUtil.isTokenValid(refreshToken, userDetails)) {
                throw new RuntimeException("无效的 refreshToken");
            }
        } else {
            // 产生新的 refreshToken
            refreshToken = fwJwtUtil.generateRefreshToken(userDetails);
        }

        // 产生新 token
        String jwtToken = fwJwtUtil.generateToken(userDetails);
        fwTokenStorage.saveToken(userDto, jwtToken);
        return FwTokenVo.builder().token(jwtToken)
                .refreshToken(refreshToken)
                .tokenWithType(FwTokenType.BEARER_ST + jwtToken)
                .build();
    }

    public FwTokenVo refreshToken(String authHeader) {
        // 如果鉴权信息为空或者不是以Bearer 开头的,直接返回
        if (authHeader == null ||!authHeader.startsWith(FwTokenType.BEARER_ST)) {
            return null;
        }
        // 从鉴权信息中获取RefreshToken
        final String refreshToken = authHeader.substring(FwTokenType.BEARER_ST.length());
        // 从RefreshToken中获取用户信息
        final String username = fwJwtUtil.extractUsername(refreshToken);
        if (username != null) {
            // 根据用户信息查询用户,如果用户不存在抛出异常
            FwUserDto userDto = fwUserBizService.findByAvailableUsername(username).orElseThrow();
            return generateNewToken(userDto, refreshToken);
        }
        return null;
    }
}
