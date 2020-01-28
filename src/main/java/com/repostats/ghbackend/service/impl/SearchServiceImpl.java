package com.repostats.ghbackend.service.impl;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.SearchDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.entity.Search;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.repository.SearchRepository;
import com.repostats.ghbackend.service.GithubService;
import com.repostats.ghbackend.service.SearchService;
import com.repostats.ghbackend.service.security.CustomOAuth2UserService;
import com.repostats.ghbackend.util.InfoMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);


    @Autowired
    private GithubService githubService;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    public UserDTO searchByGithubUsername(String username) {
        UserDTO userDTO = githubService.getUser(username);
        List<GithubRepositoryDTO> repos = githubService.getUserRepositories(username);
        userDTO.setGitRepos(repos);
        saveSearch(username);
        return userDTO;
    }

    @Override
    public List<SearchDTO> getUsernameAutoCompleteList(String usernameQuery) {
        List<Search> searchList = searchRepository.findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(customOAuth2UserService.getAuthenticatedUser().getId(), usernameQuery);
        return searchList.stream().map(searchEntity -> new SearchDTO(searchEntity.getSearchedUsername())).collect(Collectors.toList());

    }

    private Search saveSearch(String searchedUsername) {
        User currentUser = customOAuth2UserService.getAuthenticatedUser();
        Search search = new Search();
        search.setOwner(currentUser);
        search.setSearchedUsername(searchedUsername);
        if (!searchRepository.existsByOwner_IdAndSearchedUsernameIgnoreCase(currentUser.getId(), searchedUsername)) {
            logger.info(InfoMessages.SAVING_SEARCH_KEYWORD, searchedUsername, currentUser.getEmail());
            return searchRepository.saveAndFlush(search);
        } else {
            logger.warn(InfoMessages.SEARCH_KEYWORD_ALREADY_EXISTS_FOR_USER, searchedUsername, currentUser.getEmail());
            return search;
        }
    }

}
