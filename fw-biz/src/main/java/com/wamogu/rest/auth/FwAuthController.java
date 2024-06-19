package com.wamogu.rest.auth;

import com.wamogu.biz.auth.service.FwAuthBizSerivce;
import com.wamogu.security.annotation.FwAnonymousAccess;
import com.wamogu.security.model.FwPwdLoginQuery;
import com.wamogu.security.model.FwPwdRegQuery;
import com.wamogu.security.model.FwTokenVo;
import com.wamogu.security.service.FwJwtKitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Armin
 * @date 24-06-14 16:32
 */
@Getter
@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(
        name = "AUTH-授权"
)
@RequestMapping("/auth")
public class FwAuthController {
    private final FwAuthBizSerivce fwAuthBizSerivce;

    @PostMapping("/pwdRegister")
    @Operation(summary = "用户密码注册")
    @ApiResponse(responseCode = "200", description = "注册成功")
    @FwAnonymousAccess
    public FwTokenVo pwdRegister(@Valid @RequestBody FwPwdRegQuery query) {
        return fwAuthBizSerivce.pwdRegister(query);
    }


    @PostMapping("/pwdLogin")
    @Operation(summary = "用户密码登录")
    @ApiResponse(responseCode = "200", description = " 登录成功")
    @FwAnonymousAccess
    public FwTokenVo pwdLogin(@Valid @RequestBody FwPwdLoginQuery query) {
        return fwAuthBizSerivce.pwdLogin(query);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "token 刷新")
    @ApiResponse(responseCode = "200", description = "刷新成功")
    @FwAnonymousAccess
    public FwTokenVo refreshToken(String refreshToken) {
        return fwAuthBizSerivce.refreshToken(refreshToken);
    }
    @PostMapping("/logout")
    @Operation(summary = "token 登出")
    @ApiResponse(responseCode = "200", description = "登出成功")
    public void logout(@RequestHeader("Authorization") String authHeader) {
        fwAuthBizSerivce.logout(authHeader);
    }
}
