package com.repostats.ghbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GithubUsernameNotFoundException extends RuntimeException {
    public GithubUsernameNotFoundException(String message) {
        super(message);
    }
}
