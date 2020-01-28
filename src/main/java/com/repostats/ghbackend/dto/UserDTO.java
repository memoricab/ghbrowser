package com.repostats.ghbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A Response wrapper can be written for this class to justify name convention.
 * Currently fields are  directly serialized by resttemplate @{@link com.repostats.ghbackend.service.impl.GithubServiceImpl}
 */
@Data
public class UserDTO implements Serializable  {
    private static final long serialVersionUID = -762754717521626933L;
    private String name;
    private int followers;
    private String login;
    private int following;
    private String bio;
    private int public_repos;
    private String location;
    private String avatar_url;
    private List<GithubRepositoryDTO> gitRepos = new ArrayList<>();
}
