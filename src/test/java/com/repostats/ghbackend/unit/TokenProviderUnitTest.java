package com.repostats.ghbackend.unit;

import com.repostats.ghbackend.service.security.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class TokenProviderUnitTest extends AbstractUnitTest {
    private static final Date dateExpired = new Date();
    private static final Date dateNotExpired = new Date((new Date()).getTime() + mockTokenExp);

    @InjectMocks
    private TokenProvider tokenProvider;

    @Test
    public void whenCreateToken_shouldReturnString_if_authentication_notNull() {
        String token = tokenProvider.createToken(authentication);
        assertThat(token).isNotEmpty();
    }

    @Test
    public void whenGetUserIdFromToken_shouldReturnUserId() {
        assertThat(tokenProvider.getUserIdFromToken(createToken(dateNotExpired, mockTokenSecret))).isEqualTo(1L);
    }

    @Test
    public void whenValidateToken_shouldReturnFalse_if_tokenIsExpired() {
        boolean isValidated =tokenProvider.validateToken(createToken(dateExpired, mockTokenSecret));
        assertThat(isValidated).isFalse();
    }

    @Test
    public void whenValidateToken_shouldReturnTrue_if_tokenIsValid() {
        boolean isValidated =tokenProvider.validateToken(createToken(dateNotExpired, mockTokenSecret));
        assertThat(isValidated).isTrue();
    }

    @Test
    public void whenValidateToken_shouldReturnFalse_if_tokenHasInvalidSecret() {
        boolean isValidated =tokenProvider.validateToken(createToken(dateNotExpired, "FAKE_SECRET"));
        assertThat(isValidated).isFalse();
    }

    private String createToken(Date expirationDate, String tokenSecret) {
        return Jwts.builder()
                .setSubject(Long.toString(mockUserId))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

}
