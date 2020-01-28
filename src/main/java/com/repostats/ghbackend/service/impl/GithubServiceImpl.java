package com.repostats.ghbackend.service.impl;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.service.GithubService;
import com.repostats.ghbackend.service.security.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.repostats.ghbackend.util.GithubApiConst.*;

@Service
public class GithubServiceImpl implements GithubService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    public UserDTO getCurrentUser() {
        return exchange(GITHUB_API_BASE_USER_URL, new ParameterizedTypeReference<UserDTO>() {
        });
    }

    @Override
    public List<GithubRepositoryDTO> getCurrentUserRepositories() {
        return exchangeAsList(GITHUB_API_BASE_USER_REPOS_URL, new ParameterizedTypeReference<List<GithubRepositoryDTO>>() {
        });
    }

    @Override
    public List<GithubRepositoryDTO> getUserRepositories(String username) {
        String requestUrl = GITHUB_API_BASE_USERS_URL + username + REPOS_ENDPOINT;
        return exchangeAsList(requestUrl, new ParameterizedTypeReference<List<GithubRepositoryDTO>>() {
        });
    }

    @Override
    public UserDTO getUser(String username) {
        String requestUrl = GITHUB_API_BASE_USERS_URL + username;
        return exchange(requestUrl, new ParameterizedTypeReference<UserDTO>() {
        });
    }

    private <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<Object>(generateHeadersWithAccessToken()),
                responseType).getBody();
    }

    private <T> T exchange(String uri, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<Object>(generateHeadersWithAccessToken()),
                responseType).getBody();
    }

    private MultiValueMap<String, String> generateHeadersWithAccessToken() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "token " + customOAuth2UserService.getAuthenticatedUserAccessToken());
        return headers;
    }
}
