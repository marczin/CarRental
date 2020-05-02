package com.marcinrosol.carrental.security.jwt;

import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.security.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private int expiate;

    /**
     * Function generate token
     *
     * @param auth Authentication object
     * @return return generated token
     */
    public String generateToken(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        Date now = new Date();
        Date expDate = new Date(now.getTime() + expiate);

        return Jwts.builder()
                .setSubject(Long.toString(principal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    /**
     * Function get user Id from token
     *
     * @param token token in String
     * @return return user Id
     */
    public Long getUserIdFromToken(String token) {
        Claims c = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(c.getSubject());
    }

    /**
     * \
     * Function validate token
     *
     * @param token token in String
     * @return true if valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature! Message - {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token! Message - {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token! Message - {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token! Message - {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty! Message - {}", e.getMessage());
        }
        return false;
    }


}
