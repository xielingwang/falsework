package com.wamogu.security;

import cn.hutool.core.util.StrUtil;
import com.feiniaojin.gracefulresponse.advice.GrGlobalExceptionAdvice;
import com.wamogu.security.annotation.FwAnonymousAccessAware;
import com.wamogu.security.filter.FwJwtAuthFilter;
import com.wamogu.security.service.FwJwtKitService;
import com.wamogu.security.service.FwTokenStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @Author Armin
 * @date 24-06-12 23:38
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FwSecurityConfig {

    private final FwSecuritySetting fwSecuritySetting;

    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final GrGlobalExceptionAdvice grGlobalExceptionAdvice;
    private final FwJwtKitService fwJwtKitService;
    private final UserDetailsService userDetailsService;
    private final FwTokenStorage fwTokenStorage;
    private final FwAnonymousAccessAware fwAnonymousAccessAware;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info(">>>> 安全策略启动 <<<<");

        FwJwtAuthFilter fwJwtAuthFilter = new FwJwtAuthFilter(
                grGlobalExceptionAdvice, fwJwtKitService, userDetailsService, fwTokenStorage
        );
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(fwJwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        List<String> ignoreUrls = new ArrayList<>();
        Optional.ofNullable(fwSecuritySetting.getUrlWhiteList()).ifPresent(ignoreUrls::addAll);
        Optional.ofNullable(fwAnonymousAccessAware.getUrlWhiteList()).ifPresent(ignoreUrls::addAll);
        log.info(">>>> 配置白名单: \n" + StrUtil.join("\n", fwSecuritySetting.getUrlWhiteList()));
        log.info(">>>> 注解白名单: \n" + StrUtil.join("\n", fwAnonymousAccessAware.getUrlWhiteList()));

        return web -> ignoreUrls.forEach(x -> {
            String[] parts = x.split(" ");
            if (parts.length <= 1) {
                web.ignoring().requestMatchers(x);
            } else {
                web.ignoring().requestMatchers(HttpMethod.valueOf(parts[0]), parts[1]);
            }
        });
    }
}
