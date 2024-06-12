package com.wamogu.event.listener;

import com.wamogu.dao.repository.RoleRepository;
import com.wamogu.dao.repository.UserRepository;
import com.wamogu.entity.auth.Role;
import com.wamogu.entity.auth.User;
import com.wamogu.enums.UserGenderType;
import com.wamogu.po.UserProps;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author Armin
 * @date 24-06-12 18:12
 */
@Component
@RequiredArgsConstructor
public class FwStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Role role = new Role();
        role.setRoleKey("root");
        role.setRoleRemark("根用户");
        if (userRepository.count() <= 0) {
            roleRepository.save(role);
        }
        if (userRepository.count() <= 0) {
            User user = new User();
            user.setUsername("fwadmin");
            user.setPwd("fw123456");
            user.setProps(UserProps.builder().gender(UserGenderType.MALE).nation("汉").build());
            user.setRoles(Set.of(role.getRoleKey()));
            userRepository.save(user);
        }

    }
}
