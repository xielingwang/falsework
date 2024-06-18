package com.wamogu.security.service;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.HexUtil;
import com.wamogu.exception.ErrorKit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author Armin
 * @date 24-06-13 11:45
 */
@Service
public class FwJwtUtil {
    @Value("${fwapp.security.jwt.secret-key}")
    String secretKey;
    @Value("${fwapp.security.jwt.expiration}")
    long jwtExpiration;
    @Value("${fwapp.security.jwt.refresh-token.expiration}")
    long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
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
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new ErrorKit.Forbidden("token被篡改", e);
        } catch (ExpiredJwtException e) {
            throw  new ErrorKit.Forbidden("token已过期", e);
        } catch (Exception e) {
            throw new ErrorKit.Forbidden("错误的token", e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
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
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

}
