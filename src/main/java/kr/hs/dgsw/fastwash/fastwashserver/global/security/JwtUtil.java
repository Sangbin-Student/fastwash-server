package kr.hs.dgsw.fastwash.fastwashserver.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS384);

    public String issueToken(Long userId) {
        Date expiredAt = new Date();
        expiredAt.setTime(expiredAt.getTime() + 1000 * 60L * 60L * 3);

        return Jwts.builder()
                .setClaims(Map.of("user_id", userId))
                .setExpiration(expiredAt)
                .signWith(key)
                .compact();
    }

    public Long getUserIdByToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().get("user_id", Long.class);
    }
}
