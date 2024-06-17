package com.wamogu.security;

import com.wamogu.biz.auth.service.FwUserBizService;
import com.wamogu.security.model.FwUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Armin
 * @date 24-06-17 12:44
 */
@Configuration
@RequiredArgsConstructor
public class FwBasicConfig {
    private final FwUserBizService fwUserBizService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            val userDto = fwUserBizService.findByAvailableUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return FwUserDetails.valueOf(userDto, fwUserBizService.getAuthorities(userDto));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // @Bean
    // public AuditorAware<Integer> auditorAware() {
    //     return new ApplicationAuditAware();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
