package com.wamogu.security.model;

import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.entity.auth.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @Author Armin
 * @date 24-06-13 11:50
 */
@EqualsAndHashCode(callSuper = true)
public class FwUserDetails extends User implements UserDetails, CredentialsContainer {

    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    @Getter
    private Set<String> allPrivileges = new HashSet<>();

    public static FwUserDetails valueOf(FwUserDto user, Set<String> authorities) {
        FwUserDetails ret = new FwUserDetails();
        BeanUtils.copyProperties(user, ret);
        ret.allPrivileges.addAll(authorities);
        ret.grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).toList());
        return ret;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !super.isDisabled();
    }

    @Override
    public boolean isEnabled() {
        return !super.isDisabled();
    }

    @Override
    public void eraseCredentials() {
        this.setPassword(null);
    }
}
