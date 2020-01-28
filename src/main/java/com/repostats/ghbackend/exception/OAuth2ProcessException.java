package com.repostats.ghbackend.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2ProcessException extends AuthenticationException {

    public OAuth2ProcessException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2ProcessException(String msg) {
        super(msg);
    }
}
