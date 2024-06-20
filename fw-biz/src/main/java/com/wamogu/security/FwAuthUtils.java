package com.wamogu.security;

import com.wamogu.exception.ErrorKit;
import com.wamogu.security.model.FwUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

/**
 * @Author Armin
 * @date 24-06-20 12:37
 */
@UtilityClass
public class FwAuthUtils {

    public static Optional<FwUserDetails> getUserOptional() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        FwUserDetails fwUserDetails = (FwUserDetails) extractUserInfo(securityContext.getAuthentication());
        return Optional.ofNullable(fwUserDetails);
    }

    private static Object extractUserInfo(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof FwUserDetails fwUserDetails) {
            return fwUserDetails;
        }
        return null;
    }

    public static FwUserDetails getUserOrThrow() {
        Optional<FwUserDetails> optionalFwUserDetails = getUserOptional();
        if (optionalFwUserDetails.isEmpty()) {
            throw new ErrorKit.Offline();
        }
        return optionalFwUserDetails.get();
    }
    public static FwUserDetails getUserOrNull() {
        Optional<FwUserDetails> optionalFwUserDetails = getUserOptional();
        return optionalFwUserDetails.orElse(null);
    }
    public static Integer getUidOrThrow() {
        FwUserDetails optionalFwUserDetails = getUserOrThrow();
        return optionalFwUserDetails.getId();
    }
    public static Integer getUidOrNull() {
        return getUserOptional().map(FwUserDetails::getId).orElse(null);
    }

    public boolean hasRole(String role) {
        return getUserOptional().map(FwUserDetails::getRoles)
                .orElse(Set.of())
                .contains(role);
    }
    public boolean hasPrivilege(String priv) {
        return getUserOptional().map(FwUserDetails::getAllPrivileges)
                .orElse(Set.of())
                .contains(priv);
    }
}
