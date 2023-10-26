package com.yourlhub.utils;

import com.yourlhub.domain.exceptions.ApplicationErrors;
import com.yourlhub.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 7 * 24 * 60 * 60 * 1000; // 7 * 24 hour
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.issuer}")
    private String JWT_ISSUER;

    /**
     * generate a token from user's id.
     *
     * @param user yourlHub user
     * @return JWT
     */
    public String generateToken(final User user) {
        try {
            return Jwts.builder()
                    .setSubject(String.format("%s", user.getId()))
                    .setIssuer(JWT_ISSUER)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .compact();
        } catch (Throwable e) {
            throw ApplicationErrors.invalidJwt();
        }
    }

    /**
     * analyze JWT and get user's id.
     *
     * @param token JWT
     * @return user's id
     */
    public String getUserIdFromToken(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            return "";
        }
    }

}