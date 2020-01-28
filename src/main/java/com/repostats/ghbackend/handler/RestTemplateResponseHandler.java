package com.repostats.ghbackend.handler;

import com.repostats.ghbackend.exception.GithubUserNotAuthorizedException;
import com.repostats.ghbackend.exception.GithubUsernameNotFoundException;
import com.repostats.ghbackend.util.ExceptionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateResponseHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (
                clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
                        || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        if (clientHttpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            logger.error(ExceptionMessages.GITHUB_SERVER_ERROR);
        } else if (clientHttpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            logger.error(ExceptionMessages.CLIENT_BAD_REQUEST_GITHUB_ERROR, clientHttpResponse.getStatusCode());
            throwIfRequestWasUnAuthorizedInGithub(clientHttpResponse);
            throwIfRequestWasNotFoundInGithub(clientHttpResponse);

        }

    }


    private void throwIfRequestWasUnAuthorizedInGithub(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            logger.error(ExceptionMessages.GITHUB_USER_NOT_AUTHORIZED);
            throw new GithubUserNotAuthorizedException(ExceptionMessages.GITHUB_USER_NOT_AUTHORIZED);
        }
    }

    private void throwIfRequestWasNotFoundInGithub(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            logger.error(ExceptionMessages.USERNAME_NOT_FOUND_GITHUB_EXCEPTION);
            throw new GithubUsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND_GITHUB_EXCEPTION);
        }
    }
}
