package com.repostats.ghbackend.service.impl;

import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.exception.ResourceNotFoundException;
import com.repostats.ghbackend.oauth.UserPrincipal;
import com.repostats.ghbackend.service.UserService;
import com.repostats.ghbackend.util.ExceptionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> {
                    logger.error(ExceptionMessages.EMAIL_NOT_FOUND, email);
                    return new UsernameNotFoundException(ExceptionMessages.EMAIL_NOT_FOUND);
                });
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return UserPrincipal.create(user);
    }
}
