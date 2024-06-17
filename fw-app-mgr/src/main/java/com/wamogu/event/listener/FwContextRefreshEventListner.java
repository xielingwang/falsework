package com.wamogu.event.listener;

import com.wamogu.biz.auth.pojo.FwPrivilegeDto;
import com.wamogu.biz.auth.pojo.FwRoleDto;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.biz.auth.service.FwPrivilegeBizService;
import com.wamogu.biz.auth.service.FwRoleBizService;
import com.wamogu.biz.auth.service.FwUserBizService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Armin
 * @date 24-06-12 18:12
 */
@Component
@RequiredArgsConstructor
public class FwContextRefreshEventListner implements
        ApplicationListener<ApplicationStartedEvent> {

    boolean alreadySetup = false;

    private final FwUserBizService userBizService;

    private final FwRoleBizService roleBizService;

    private final FwPrivilegeBizService privilegeBizService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        if (alreadySetup)
            return;


        FwPrivilegeDto readPrivilege = createPrivilegeIfNotFound("PRIV_READ");
        FwPrivilegeDto writePrivilege = createPrivilegeIfNotFound("PRIV_WRITE");

        List<FwPrivilegeDto> adminPrivileges = List.of(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        FwRoleDto adminRole = roleBizService.findByKey("ROLE_ADMIN");

        FwUserDto user = userBizService.findByAvailableUsername("fwadmin").orElse(null);
        if (user == null) {
            user = new FwUserDto();
            user.setUsername("fwadmin");
            user.setGivenName("Admin");
            user.setFamilyName("Falsework");
            user.setPassword(passwordEncoder.encode("fw123456"));
            user.setRoles(Set.of(adminRole.getRoleKey()));
            user.setDisabled(false);
            userBizService.createOne(user);
        }

        alreadySetup = true;
    }

    @Transactional
    FwPrivilegeDto createPrivilegeIfNotFound(String privKey) {

        FwPrivilegeDto dto = privilegeBizService.findByKey(privKey);
        if (dto == null) {
            dto = new FwPrivilegeDto();
            dto.setPrivilegeKey(privKey);
            dto.setPrivilegeRemark(privKey+"说明");
            privilegeBizService.createOne(dto);
        }
        return dto;
    }

    @Transactional
    FwRoleDto createRoleIfNotFound(
            String roleKey, List<FwPrivilegeDto> privileges) {

        FwRoleDto role = roleBizService.findByKey(roleKey);
        if (role == null) {
            role = new FwRoleDto();
            role.setRoleKey(roleKey);
            role.setRoleRemark(roleKey+"说明");
            role.setPrivileges(privileges.stream().map(FwPrivilegeDto::getPrivilegeKey).collect(Collectors.toSet()));
            roleBizService.createOne(role);
        }
        return role;
    }
}
