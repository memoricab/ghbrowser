package com.repostats.ghbackend.util;

public class GithubApiConst {
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    public static final String REPOS_ENDPOINT = "/repos";
    public static final String GITHUB_API_BASE_USER_URL = GITHUB_API_BASE_URL + "/user";
    public static final String GITHUB_API_BASE_USER_REPOS_URL = GITHUB_API_BASE_USER_URL + REPOS_ENDPOINT;
    public static final String GITHUB_API_BASE_USERS_URL = GITHUB_API_BASE_URL + "/users/";
}
