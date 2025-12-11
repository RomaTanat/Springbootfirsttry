package org.example.helloworld.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // 1. Секретный ключ из application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // 2. Время жизни токена из application.properties
    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    // --- Генерация Токена (после успешного входа) ---
    public String generateJwtToken(Authentication authentication) {
        // Получаем объект пользователя Spring Security
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Имя пользователя (Субъект)
                .setIssuedAt(new Date()) // Время выпуска
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Время истечения
                .signWith(key(), SignatureAlgorithm.HS512) // Подпись ключом
                .compact(); // Упаковка
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // --- Извлечение Имени Пользователя ---
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // --- Валидация Токена (Проверка подписи и срока годности) ---
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}