package com.repostats.ghbackend.service.security;

import com.repostats.ghbackend.config.AppProperties;
import com.repostats.ghbackend.oauth.UserPrincipal;
import com.repostats.ghbackend.util.ExceptionMessages;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error(ExceptionMessages.INVALID_JWT_SIGNATURE, token);
        } catch (MalformedJwtException ex) {
            logger.error(ExceptionMessages.INVALID_JWT_TOKEN, token);
        } catch (ExpiredJwtException ex) {
            logger.error(ExceptionMessages.EXPIRED_JWT_TOKEN, token);
        } catch (UnsupportedJwtException ex) {
            logger.error(ExceptionMessages.UNSUPPORTED_JWT_TOKEN, token);
        } catch (IllegalArgumentException ex) {
            logger.error(ExceptionMessages.EMPTY_JWT_TOKEN);
        }
        return false;
    }
}
