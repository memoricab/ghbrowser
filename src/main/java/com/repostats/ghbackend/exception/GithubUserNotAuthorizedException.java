package com.repostats.ghbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class GithubUserNotAuthorizedException extends RuntimeException {

    public GithubUserNotAuthorizedException(String message) {
        super(message);
    }
}
