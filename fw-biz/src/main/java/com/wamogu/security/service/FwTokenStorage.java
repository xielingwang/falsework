/*
 * Falsework is a quick development framework
 * Copyright (C) 2015-2015 挖蘑菇技术部  https://tech.wamogu.com
 */
package com.wamogu.security.service;

import com.wamogu.biz.auth.pojo.FwUserDto;
import java.beans.ConstructorProperties;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Armin
 *
 * @date 24-06-14 15:18
 */
public interface FwTokenStorage {

    Optional<FwToken> findByToken(String jwt);

    void saveToken(FwUserDto userDto, String jwtToken);

    void expireTokenHistories(FwUserDto userDto);

    void expire(FwToken storedToken);

    @Setter
    @Getter
    @Builder
    class FwToken {

        private String token;

        private Integer uid;

        private boolean expired;

        private boolean revoked;

        private String tokenType;

        @ConstructorProperties({"token", "uid", "expired", "revoked", "tokenType"})
        public FwToken(String token, Integer uid, boolean expired, boolean revoked, String tokenType) {
            this.token = token;
            this.uid = uid;
            this.expired = expired;
            this.revoked = revoked;
            this.tokenType = tokenType;
        }
    }
}
