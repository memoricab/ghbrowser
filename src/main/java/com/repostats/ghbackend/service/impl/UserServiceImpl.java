package com.repostats.ghbackend.service.impl;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.entity.AuthProvider;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.oauth.pojo.OAuth2UserInfo;
import com.repostats.ghbackend.repository.UserRepository;
import com.repostats.ghbackend.service.GithubService;
import com.repostats.ghbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GithubService githubService;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDTO getCurrentUserData() {
        List<GithubRepositoryDTO> repos = githubService.getCurrentUserRepositories();
        UserDTO currentUserDto = githubService.getCurrentUser();
        currentUserDto.setGitRepos(repos);
        return currentUserDto;
    }

    @Override
    public User saveOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setGithubAccessToken(oAuth2UserRequest.getAccessToken().getTokenValue());
        return userRepository.save(user);
    }

    @Override
    public User updateOAuth2User(User userToUpdate, OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        userToUpdate.setUsername(oAuth2UserInfo.getUsername());
        userToUpdate.setGithubAccessToken(oAuth2UserRequest.getAccessToken().getTokenValue());
        return userRepository.saveAndFlush(userToUpdate);
    }
}
