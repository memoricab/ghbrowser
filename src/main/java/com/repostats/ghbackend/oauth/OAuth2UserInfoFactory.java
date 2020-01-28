package com.repostats.ghbackend.oauth;

import com.repostats.ghbackend.entity.AuthProvider;
import com.repostats.ghbackend.exception.OAuth2ProcessException;
import com.repostats.ghbackend.oauth.pojo.GitHubOAuth2UserInfo;
import com.repostats.ghbackend.oauth.pojo.OAuth2UserInfo;
import com.repostats.ghbackend.util.ExceptionMessages;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GitHubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2ProcessException(ExceptionMessages.AUTH_PROCESS_EXCEPTION_REG_ID_NOT_FOUND);
        }
    }
}
