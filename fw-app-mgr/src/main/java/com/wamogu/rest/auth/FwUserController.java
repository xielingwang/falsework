package com.wamogu.rest.auth;

import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.biz.auth.pojo.FwUserQuery;
import com.wamogu.biz.auth.pojo.FwUserVo;
import com.wamogu.biz.auth.service.FwUserBizService;
import com.wamogu.entity.auth.User;
import com.wamogu.kit.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-12
 */
@Getter
@ResponseBody
@RestController
@RequiredArgsConstructor
@Tag(
        name = "AUTH-用户表"
)
@RequestMapping("/user")
public final class FwUserController extends BaseController<User, FwUserDto, FwUserVo, FwUserQuery, Integer> {
    private final FwUserBizService bizService;
}