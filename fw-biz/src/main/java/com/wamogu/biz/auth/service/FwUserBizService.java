package com.wamogu.biz.auth.service;

import com.wamogu.biz.auth.convert.FwUserCastor;
import com.wamogu.biz.auth.pojo.FwRoleDto;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.biz.auth.pojo.FwUserVo;
import com.wamogu.dao.repository.UserRepository;
import com.wamogu.entity.auth.User;
import com.wamogu.enums.UserIdentifyType;
import com.wamogu.kit.BaseBizService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * generated by FwUtilCodegen
 * @since 2024-06-12
 */
@Getter
@Service
@RequiredArgsConstructor
public final class FwUserBizService extends BaseBizService<User, FwUserDto, FwUserVo, Integer> {
    private final FwUserCastor baseCastor;

    private final UserRepository baseRepository;
    private final FwRoleBizService fwRoleBizService;

    // Cached
    public Optional<FwUserDto> findByAvailableUsername(String username) {
        FwUserDto ret = baseCastor.do2dto(baseRepository.lambdaQuery()
                .eq(User::getUsername, username)
                .one());
        // find in UserIdentify
        return Optional.ofNullable(ret);
    }

    // Cached
    public Set<String> getAuthorities(FwUserDto user) {
        Set<String> result = new HashSet<>();

        Optional.ofNullable(user.getPrivileges()).ifPresent(result::addAll);
        Optional.ofNullable(user.getRoles()).ifPresent(roles -> {
            result.addAll(roles);
            fwRoleBizService.findByKeys(roles)
                    .stream().map(FwRoleDto::getPrivileges)
                    .forEach(result::addAll);
        });
        return result;
    }
}
