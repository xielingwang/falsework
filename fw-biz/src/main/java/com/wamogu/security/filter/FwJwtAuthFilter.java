package com.wamogu.security.filter;

import com.wamogu.security.constants.FwTokenType;
import com.wamogu.security.service.FwJwtUtil;
import com.wamogu.security.service.FwTokenStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author Armin
 * @date 24-06-13 11:45
 */
// @Component
@Slf4j
@RequiredArgsConstructor
@Component
public class FwJwtAuthFilter extends OncePerRequestFilter {
    private final FwJwtUtil fwJwtUtil;

    private final UserDetailsService userDetailsService;
    private final FwTokenStorage fwTokenStorage;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/auth") && (servletPath.contains("Login") || servletPath.contains("Reg"))) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith(FwTokenType.BEARER_ST)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(FwTokenType.BEARER_ST.length());
        userEmail = fwJwtUtil.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = fwTokenStorage.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (fwJwtUtil.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
