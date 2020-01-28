package com.repostats.ghbackend.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String searchedUsername;

    public SearchDTO(String searchedUsername) {
        this.searchedUsername = searchedUsername;
    }
}
