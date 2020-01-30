package com.repostats.ghbackend.unit;

import com.repostats.ghbackend.config.AppProperties;
import com.repostats.ghbackend.oauth.UserPrincipal;
import com.repostats.ghbackend.service.security.TokenProvider;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractUnitTests {
    protected static final long mockUserId = 1L;
    protected static final String mockUserEmail = "email@email.com";
    protected static final long mockTokenExp = 10000L;
    protected static final String mockTokenSecret = "SECRET";


    @Mock
    protected AppProperties appProperties;

    @Mock
    protected Authentication authentication;


    @Bean
    public Authentication authentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new UserPrincipal(mockUserId, mockUserEmail, authorities), null, authorities);
        return authentication;
    }

    @Bean
    public AppProperties appProperties() {
        return new AppProperties();
    }

    @Before
    public void setUp() throws Exception {
        this.authentication = authentication();
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        AppProperties.Auth auth = new AppProperties.Auth();
        auth.setTokenExpirationMsec(mockTokenExp);
        auth.setTokenSecret(mockTokenSecret);
        when(appProperties.getAuth()).thenReturn(auth);
    }
}
