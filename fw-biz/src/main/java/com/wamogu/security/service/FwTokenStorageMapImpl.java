package com.wamogu.security.service;

import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.security.constants.FwTokenType;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Armin
 * @date 24-06-14 15:20
 */
@Service
public class FwTokenStorageMapImpl implements FwTokenStorage {
    private ConcurrentMap<String, FwToken> map = new ConcurrentReferenceHashMap<>();
    @Override
    public Optional<FwToken> findByToken(String jwt) {
        return Optional.ofNullable(map.get(jwt));
    }

    @Override
    public void saveToken(FwUserDto userDto, String jwtToken) {

        // 撤销该用户历史token
        expireTokenHistories(userDto);

        // 保存
        FwToken fwToken = FwToken.builder()
                .token(jwtToken)
                .uid(userDto.getId())
                .revoked(false)
                .expired(false)
                .tokenType(FwTokenType.BEARER)
                .build();
        map.put(jwtToken, fwToken);
    }

    @Override
    public void expireTokenHistories(FwUserDto userDto) {
        map.values().stream().filter(x -> x.getUid().equals(userDto.getId()))
                .forEach(x -> {
                    x.setRevoked(true);
                    x.setExpired(true);
                });
    }

    @Override
    public void expire(FwToken storedToken) {
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        map.put(storedToken.getToken(), storedToken);
    }
}
