package com.repostats.ghbackend.service;

import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.oauth.pojo.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    UserDTO getCurrentUserData();
    User saveOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);
    User updateOAuth2User(User userToUpdate, OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo);
}
