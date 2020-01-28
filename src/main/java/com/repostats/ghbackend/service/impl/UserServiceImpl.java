package com.repostats.ghbackend.service.impl;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.repository.UserRepository;
import com.repostats.ghbackend.service.GithubService;
import com.repostats.ghbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
