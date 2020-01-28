package com.repostats.ghbackend.handler;

import com.repostats.ghbackend.config.AppProperties;
import com.repostats.ghbackend.exception.BadRequestException;
import com.repostats.ghbackend.repository.impl.HttpCookieOAuth2RequestRepository;
import com.repostats.ghbackend.service.security.TokenProvider;
import com.repostats.ghbackend.util.CookieUtils;
import com.repostats.ghbackend.util.ExceptionMessages;
import com.repostats.ghbackend.util.InfoMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.repostats.ghbackend.repository.impl.HttpCookieOAuth2RequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private TokenProvider tokenProviderService;
    private AppProperties appProperties;
    private HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;


    @Autowired
    OAuth2AuthenticationSuccessHandler(TokenProvider tokenProviderService, AppProperties appProperties,
                                       HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository) {
        this.tokenProviderService = tokenProviderService;
        this.appProperties = appProperties;
        this.httpCookieOAuth2RequestRepository = httpCookieOAuth2RequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        ;

        if (response.isCommitted()) {
            logger.info(InfoMessages.RESPONSE_ALREADY_COMMITED + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException(ExceptionMessages.UNAUTHORIZED_REDIRECT_URI);
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = tokenProviderService.createToken(authentication);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
