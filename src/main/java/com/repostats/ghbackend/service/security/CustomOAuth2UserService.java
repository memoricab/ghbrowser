package com.repostats.ghbackend.service.security;


import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.exception.OAuth2ProcessException;
import com.repostats.ghbackend.oauth.OAuth2UserInfoFactory;
import com.repostats.ghbackend.oauth.UserPrincipal;
import com.repostats.ghbackend.oauth.pojo.OAuth2UserInfo;
import com.repostats.ghbackend.service.UserService;
import com.repostats.ghbackend.util.ExceptionMessages;
import com.repostats.ghbackend.util.InfoMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserService userService;

    public CustomOAuth2UserService(@Lazy UserService userService) {
        this.userService = userService; // userService is here for only accessing userRepository; therefore lazy initialization to prevent from circular injection of userService
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    public String getAuthenticatedUserAccessToken() {
        return getAuthenticatedUser().getGithubAccessToken();
    }

    public User getAuthenticatedUser() {
        Optional<User> optionalUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND));
    }


    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2ProcessException(ExceptionMessages.AUTH_PROCESS_EXCEPTION_EMAIL_NOT_FOUND);
        }
        Optional<User> userOptional = userService.getUserByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            logger.info(InfoMessages.PROCESSING_OAUTH2_USER_UPDATE, userOptional.get().getEmail());
            user = userOptional.get();
            user = userService.updateOAuth2User(user, oAuth2UserRequest, oAuth2UserInfo);
        } else {
            logger.info(InfoMessages.PROCESSING_OAUTH2_USER_NEW, oAuth2UserInfo.getEmail());
            user = userService.saveOAuth2User(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}
