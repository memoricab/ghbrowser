package com.repostats.ghbackend.service;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.UserDTO;

import java.util.List;

public interface GithubService {

    UserDTO getCurrentUser();
    List<GithubRepositoryDTO> getCurrentUserRepositories();
    List<GithubRepositoryDTO> getUserRepositories(String username);
    UserDTO getUser(String username);

}
