package com.wamogu.security.service;

import com.wamogu.biz.auth.pojo.FwUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @Author Armin
 * @date 24-06-14 15:18
 */
public interface FwTokenStorage {

    Optional<FwToken> findByToken(String jwt);

    void saveToken(FwUserDto userDto, String jwtToken);

    void expireTokenHistories(FwUserDto userDto);

    void expire(FwToken storedToken);
    void expire(String token);

    @Setter
    @Getter
    @Builder
    class FwToken {
        private String token;
        private Integer uid;
        private boolean expired;
        private boolean revoked;
        private String tokenType;
    }
}