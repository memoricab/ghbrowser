package com.repostats.ghbackend.integration;

import com.repostats.ghbackend.dto.SearchDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SearchRestControllerIntegrationTest extends AbstractSpringBootTest {

    @MockBean
    private SearchService searchService;


    @WithMockUser
    @Test
    public void whenSearchUserByUsername_shouldReturn_userDTO_if_paramIsNotEmpty() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("searchedUsername");
        when(searchService.searchByGithubUsername(any(String.class))).thenReturn(userDTO);

        mockMvc.perform(get("/api/search?username=searchedUsername").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("searchedUsername"));

    }

    @WithMockUser
    @Test
    public void whenSearchUserByUsername_shouldReturn_400_if_paramIsEmpty() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("searchedUsername");
        when(searchService.searchByGithubUsername(any(String.class))).thenReturn(userDTO);

        mockMvc.perform(get("/api/search?username=").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @WithMockUser
    @Test
    public void whenQueryToAutoCompleOfUsername_shouldReturn_ListSearchDTO_if_paramIsNotEmpty() throws Exception {
        SearchDTO searchDTO1 = new SearchDTO("searchedUsername1");
        SearchDTO searchDTO2 = new SearchDTO("searchedUsername2");
        List<SearchDTO> searchList = new ArrayList<SearchDTO>(Arrays.asList(searchDTO1, searchDTO2));
        when(searchService.getUsernameAutoCompleteList(any(String.class))).thenReturn(searchList);

        mockMvc.perform(get("/api/search/?autoCompleteQuery=searchedUsername").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].searchedUsername").value("searchedUsername1"))
                .andExpect(jsonPath("[1].searchedUsername").value("searchedUsername2"));


    }

    @WithMockUser
    @Test
    public void whenQueryToAutoCompleOfUsername_shouldReturn_ListSearchDTO_if_paramIsEmpty() throws Exception {
        SearchDTO searchDTO1 = new SearchDTO("searchedUsername1");
        SearchDTO searchDTO2 = new SearchDTO("searchedUsername2");
        List<SearchDTO> searchList = new ArrayList<SearchDTO>(Arrays.asList(searchDTO1, searchDTO2));
        when(searchService.getUsernameAutoCompleteList(any(String.class))).thenReturn(searchList);

        mockMvc.perform(get("/api/search/?autoCompleteQuery=").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].searchedUsername").value("searchedUsername1"))
                .andExpect(jsonPath("[1].searchedUsername").value("searchedUsername2"));


    }

    @Test
    public void whenUnAuthRequestToAPI_shouldReturn_401Unauth() throws Exception {
        mockMvc.perform(get("/api/search/?autoCompleteQuery=").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/search?username=").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

}
