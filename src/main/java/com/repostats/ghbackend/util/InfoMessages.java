package com.repostats.ghbackend.util;

public class InfoMessages {
    public static final String UNAUTHORIZED = "Unauthorized! Message: {}" ;

    //Search Service
    public static final String SAVING_SEARCH_KEYWORD = "Saved search keyword: {} to database for user: {} ";
    public static final String SEARCH_KEYWORD_ALREADY_EXISTS_FOR_USER = "Search keyword: {} already exists for user: {}, not saving to database";

    //OAUTH2 SUCCESS HANDLER
    public static final String RESPONSE_ALREADY_COMMITED = "Response has already been committed. Unable to redirect to ";

    // OAUTH2 USER SERVICE
    public static final String PROCESSING_OAUTH2_USER_UPDATE = "User: {} already registered. Updating Github access info.";
    public static final String PROCESSING_OAUTH2_USER_NEW = "User: {} is being registered to system.";
}
