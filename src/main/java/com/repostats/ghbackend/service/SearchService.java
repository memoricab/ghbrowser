package com.repostats.ghbackend.service;

import com.repostats.ghbackend.dto.SearchDTO;
import com.repostats.ghbackend.dto.UserDTO;

import java.util.List;

public interface SearchService {
    UserDTO searchByGithubUsername(String username);
    List<SearchDTO> getUsernameAutoCompleteList(String usernameQuery);
}
