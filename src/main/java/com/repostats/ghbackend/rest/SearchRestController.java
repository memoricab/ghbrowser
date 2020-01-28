package com.repostats.ghbackend.rest;

import com.repostats.ghbackend.dto.SearchDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchRestController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public UserDTO searchUserByUsername(@RequestParam String username) {
        return searchService.searchByGithubUsername(username);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public List<SearchDTO> queryToAutoCompleteOfUsername(@RequestParam String autoCompleteQuery) {
        return searchService.getUsernameAutoCompleteList(autoCompleteQuery);
    }

}
