package com.wamogu.security.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.kit.FwJsonUtils;
import com.wamogu.security.constants.FwTokenType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Armin
 * @date 24-06-14 15:20
 */
@Service
public class FwTokenStorageMapImpl implements FwTokenStorage, InitializingBean, DisposableBean {
    private Map<String, FwToken> tokenMap = new ConcurrentHashMap<>();
    @Override
    public Optional<FwToken> findByToken(String jwt) {
        return Optional.ofNullable(tokenMap.get(jwt));
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
        tokenMap.put(jwtToken, fwToken);
    }

    @Override
    public void expireTokenHistories(FwUserDto userDto) {
        tokenMap.values().stream().filter(x -> x.getUid().equals(userDto.getId()))
                .forEach(x -> {
                    x.setRevoked(true);
                    x.setExpired(true);
                });
    }

    @Override
    public void expire(FwToken storedToken) {
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenMap.put(storedToken.getToken(), storedToken);
    }

    public String getSessionPath() {
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir + (tempDir.endsWith(File.separator) ? "" : File.separator) + "session.json";
    }

    @Override
    public void destroy() throws Exception {
        FileWriter fileWriter = new FileWriter(getSessionPath());
        fileWriter.write(FwJsonUtils.bean2json(tokenMap.values()));
        fileWriter.close();
    }

    @Override
    public void afterPropertiesSet() {
        if (!FileUtil.exist(getSessionPath())) {
            return;
        }
        FileReader fileReader = new FileReader(getSessionPath());
        FwJsonUtils.json2list(fileReader.readString(), FwToken.class)
                .forEach(x -> tokenMap.put(x.getToken(), x));
    }
}
