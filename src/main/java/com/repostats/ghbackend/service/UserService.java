package com.repostats.ghbackend.service;

import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    UserDTO getCurrentUserData();

}
