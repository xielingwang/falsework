package com.wamogu.security.service;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import com.wamogu.biz.auth.pojo.FwUserDto;
import com.wamogu.biz.auth.service.FwUserBizService;
import com.wamogu.exception.ErrorKit;
import com.wamogu.security.constants.FwTokenType;
import com.wamogu.security.model.FwTokenVo;
import com.wamogu.security.model.FwUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author Armin
 * @date 24-06-13 11:45
 */
@Service
@RequiredArgsConstructor
public class FwJwtKitService {
    @Value("${fwapp.security.jwt.secret-key}")
    String secretKey;
    @Value("${fwapp.security.jwt.token-life-minutes}")
    long jwtLifeMinutes;
    @Value("${fwapp.security.jwt.refresh-token-life-minutes}")
    long jwtRefreshLifeMinutes;

    private final FwTokenStorage fwTokenStorage;
    private final FwUserBizService fwUserBizService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtLifeMinutes);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, jwtRefreshLifeMinutes);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long lifeMinutes
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(DateTime.now())
                .expiration(DateTime.now().offsetNew(DateField.MINUTE, (int) lifeMinutes))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            JwtParser parser = Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build();
            return parser.parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw  new ErrorKit.Forbidden("token 已失效", e);
        } catch (Exception e) {
            throw new ErrorKit.Forbidden("token 相关的未知错误", e);
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public static void main(String[] args) {
        SecretKey sk = genSecretKey();
        System.out.println("secret key: " + tohexSecretKey(sk));
        System.out.println("secret key: " + toBase64SecretKey(sk));
    }

    private static String toBase64SecretKey(SecretKey sk) {
        return Base64Encoder.encode(sk.getEncoded());
    }

    private static String tohexSecretKey(SecretKey sk) {
        return HexUtil.encodeHexStr(sk.getEncoded()).toUpperCase();
    }

    private static SecretKey genSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String extractTokenFromHeader(String authHeader) {
        if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith(FwTokenType.BEARER_ST)) {
            return StrUtil.subAfter(authHeader, FwTokenType.BEARER_ST, false);
        }
        return authHeader;
    }

    public FwTokenVo generateTokenForLogin(FwUserDto userDto) {
        return generateTokenForRefresh(userDto, null);
    }
    public FwTokenVo generateTokenForRefresh(FwUserDto userDto, String refreshToken) {
        FwUserDetails userDetails = FwUserDetails.valueOf(userDto, fwUserBizService.getAuthorities(userDto));

        // 验证Token是否有效
        if (null != refreshToken) {
            if (!isTokenValid(refreshToken, userDetails)) {
                throw new RuntimeException("无效的 refreshToken");
            }
        }

        // 产生新的 refreshToken
        else {
            refreshToken = generateRefreshToken(userDetails);
        }

        // 产生新 token
        String jwtToken = generateToken(userDetails);
        fwTokenStorage.saveToken(userDto, jwtToken);
        return FwTokenVo.builder().token(jwtToken)
                .refreshToken(refreshToken)
                .currentTime(LocalDateTime.now())
                .build();
    }
}
