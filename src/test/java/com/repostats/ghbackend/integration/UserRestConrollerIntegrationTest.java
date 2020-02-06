package com.repostats.ghbackend.integration;

import com.repostats.ghbackend.dto.GithubRepositoryDTO;
import com.repostats.ghbackend.dto.UserDTO;
import com.repostats.ghbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserRestConrollerIntegrationTest extends AbstractSpringBootTest {

    @MockBean
    private UserService userService;


    @WithMockUser
    @Test
    public void whenGetCurrentUser_shouldReturn_currentUserData() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("currentUser");

        GithubRepositoryDTO githubRepositoryDTO = new GithubRepositoryDTO();
        githubRepositoryDTO.setName("test_repo");

        List<GithubRepositoryDTO> repoList = new ArrayList<>();
        repoList.add(githubRepositoryDTO);
        userDTO.setGitRepos(repoList);

        when(userService.getCurrentUserData()).thenReturn(userDTO);


        mockMvc.perform(get("/api/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("currentUser"))
                .andExpect(jsonPath("gitRepos[0].name").value("test_repo"));

    }


    @Test
    public void whenGetCurrentUser_shouldReturn_401_whenNotAuthenticatedUser() throws Exception {
        when(userService.getCurrentUserData()).thenReturn(new UserDTO());


        mockMvc.perform(get("/api/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
