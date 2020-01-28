package com.repostats.ghbackend.util;

public class ExceptionMessages {
    public static final String RESOURCE_NOT_FOUND_EXCEPTION = "%s not found with %s : '%s'";
    public static final String USERNAME_NOT_FOUND_GITHUB_EXCEPTION = "Username not found in github.";
    public static final String GITHUB_SERVER_ERROR = "Github server returned with server error";
    public static final String CLIENT_BAD_REQUEST_GITHUB_ERROR = "A bad request executed to github server. {}";
    public static final String GITHUB_USER_NOT_AUTHORIZED = "This request is not authorized in Github server";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String AUTH_PROCESS_EXCEPTION_REG_ID_NOT_FOUND = "Registration Id not recognized";
    public static final String AUTH_PROCESS_EXCEPTION_EMAIL_NOT_FOUND = "Email not recognized from provider. You need to add public email to OAuth2 Provider i.e. GitHub";
    public static final String USER_AUTH_NOT_SET_INTO_CONTEXT = "User Authentication is not added to security context" ;


    //TOKEN PROVIDER
    private static final String TOKEN = "Token: {}";
    public static final String INVALID_JWT_SIGNATURE = "Invalid JWT Signature." + TOKEN;
    public static final String INVALID_JWT_TOKEN = "Invalid JWT " + TOKEN;
    public static final String EXPIRED_JWT_TOKEN = "EXPIRED JWT " + TOKEN;
    public static final String UNSUPPORTED_JWT_TOKEN = "Unsupported JWT " + TOKEN;
    public static final String EMPTY_JWT_TOKEN = "Empty JWT Token";


    // OAUTH2 SUCCESS HANDLER
    public static final String UNAUTHORIZED_REDIRECT_URI ="Unauthorized Redirect URI and can't proceed with the authentication";


    //CUSTOM USER DETAILS
    public static final String EMAIL_NOT_FOUND = "Email: {} not found";
}
