package de.share_your_idea.user_meeting.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("$(jwt.secret-key)")
    private String secretKey;

    public String generateToken(String username) {
        if(!username.isBlank()) {
            log.info("JwtProvider: GenerateToken Method is called");
            Date expirationDate = Date.from(LocalDate.now().plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        }
        return null;
    }

    public boolean validateToken(String token) {
        log.info("JwtProvider: ValidateToken Method is called");
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token is expired.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupported.", e);
        } catch (MalformedJwtException e) {
            log.error("Token is malformed.", e);
        } catch (SignatureException e) {
            log.error("Token is invalid Signature.", e);
        } catch (IllegalArgumentException e) {
            log.error("Token has invalid Argument.", e);
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        log.info("JwtProvider: GetUsernameFromToken Method is called");
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
