package workhive.app.global.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import workhive.app.global.dto.TokenDto;
import workhive.app.global.handler.RedisHandler;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

import static workhive.app.global.utils.RedisKeyGenerator.getJwtKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.access.expire}")
    private Long accessExpire;

    @Value("${jwt.refresh.expire}")
    private Long refreshExpire;

    private static final int KEY_SIZE = 256;
    private SecretKey key;

    private final RedisHandler redisHandler;

    @PostConstruct
    public void init() {
        byte[] keyBytes = new byte[KEY_SIZE / 8];
        byte[] decoded = Base64.getDecoder().decode(secret);
        System.arraycopy(decoded, 0, keyBytes, 0, Math.min(decoded.length, keyBytes.length));
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(final String username) {
        String jwtKey = getJwtKey(username);
        // 1. 기존에 생성된 토큰 정보를 삭제
        redisHandler.delete(jwtKey);

        // 2. Access Token 생성
        String accessToken = generateAccessToken(username);

        // 3. Refresh Token 생성
        String refreshToken = generateRefreshToken();

        // 4. Redis에 Token 저장
         redisHandler.execute(() -> {
             redisHandler.getHashOperations().put(jwtKey, "accessToken", accessToken);
             redisHandler.getHashOperations().put(jwtKey, "refreshToken", refreshToken);
             redisHandler.setExpire(jwtKey, refreshExpire);
         });

        long now = new Date().getTime();
        return TokenDto.builder()
                 .accessToken(accessToken)
                 .refreshToken(refreshToken)
                 .accessTokenExpiredAt(toLocalDateTime(now + accessExpire))
                    .refreshTokenExpiredAt(toLocalDateTime(now + refreshExpire))
                 .build();

    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        Date now = new Date();
        long nowMillis = now.getTime();
        long expiredMillis = nowMillis + accessExpire;

        return Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(expiredMillis))
                .compact();
    }

    public String generateRefreshToken() {
        Date now = new Date();
        long nowMillis = now.getTime();
        long expiredMillis = nowMillis + refreshExpire;

        return Jwts.builder()
                .signWith(key, Jwts.SIG.HS256)
                .issuedAt(now)
                .expiration(new Date(expiredMillis))
                .compact();
    }

    public Boolean validateToken(final String token) {
        try {
            Objects.requireNonNull(token);
            String userIdFromToken = getUserIdFromToken(token);

            Object accessToken = redisHandler.getHashOperations()
                    .get(getJwtKey(userIdFromToken), "accessToken");

            if (accessToken == null) {
                log.info("Access token is not found in Redis");
                return false;
            }

            return String.valueOf(accessToken).equals(token);
        } catch (SecurityException e) {
            log.info("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty: {}", e.getMessage());
        } catch (NullPointerException e) {
            log.info("JWT token is null: {}", e.getMessage());
        }
        return false;
    }

    public String getUserIdFromToken(final String token) {
        return getClaimFromToken(token, claims ->
           claims.get("userId", String.class)
        );
    }

    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private LocalDateTime toLocalDateTime(long millis) {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
