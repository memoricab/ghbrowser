package com.repostats.ghbackend.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A Response wrapper can be written for this class to justify name convention.
 * Currently fields are  directly serialized by resttemplate @{@link com.repostats.ghbackend.service.impl.GithubServiceImpl}
 */
@Data
public class GithubRepositoryDTO implements Serializable {
    private static final long serialVersionUID = 2474946759718686209L;

    private String name;
    private String full_name;
    private String html_url;

}
