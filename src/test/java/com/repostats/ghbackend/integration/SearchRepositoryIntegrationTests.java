package com.repostats.ghbackend.integration;

import com.repostats.ghbackend.entity.AuthProvider;
import com.repostats.ghbackend.entity.Search;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.repository.SearchRepository;
import com.repostats.ghbackend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SearchRepositoryIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private UserRepository userRepository;

    private User mockUser1;
    private User mockUser2;

    @Before
    public void contextLoads() {
        mockUser1 = new User();
        mockUser1.setEmail("email@email.com");
        mockUser1.setUsername("username");
        mockUser1.setProvider(AuthProvider.github);
        mockUser1.setGithubAccessToken("123");
        userRepository.save(mockUser1);

        Search searchA_BelongsToUser1 = new Search();
        searchA_BelongsToUser1.setSearchedUsername("Alice");
        searchA_BelongsToUser1.setOwner(mockUser1);
        searchRepository.save(searchA_BelongsToUser1);

        Search searchB_BelongsToUser1 = new Search();
        searchB_BelongsToUser1.setSearchedUsername("Brooklyn");
        searchB_BelongsToUser1.setOwner(mockUser1);
        searchRepository.save(searchB_BelongsToUser1);

        mockUser2 = new User();
        mockUser2.setEmail("email1@email.com");
        mockUser2.setUsername("username1");
        mockUser2.setProvider(AuthProvider.github);
        mockUser2.setGithubAccessToken("321");
        userRepository.save(mockUser2);

        Search searchX_BelongsToUser2 = new Search();
        searchX_BelongsToUser2.setSearchedUsername("Xenon");
        searchX_BelongsToUser2.setOwner(mockUser2);
        searchRepository.save(searchX_BelongsToUser2);

        Search searchY_BelongsToUser2 = new Search();
        searchY_BelongsToUser2.setSearchedUsername("Yo-yo");
        searchY_BelongsToUser2.setOwner(mockUser2);
        searchRepository.save(searchY_BelongsToUser2);


    }


    @Test
    public void findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase() {

        List<Search> searchList1_User1 = searchRepository.findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(mockUser1.getId(), "a");
        List<Search> searchList2_User1 = searchRepository.findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(mockUser1.getId(), "b");
        List<Search> searchList1_User2 = searchRepository.findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(mockUser2.getId(), "x");
        List<Search> searchList2_User2 = searchRepository.findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(mockUser2.getId(), "y");

        searchList1_User1.forEach(search-> assertThat(search.getSearchedUsername()).startsWith("A"));
        searchList2_User1.forEach(search-> assertThat(search.getSearchedUsername()).startsWith("B"));
        searchList1_User2.forEach(search-> assertThat(search.getSearchedUsername()).startsWith("Xe"));
        searchList2_User2.forEach(search-> assertThat(search.getSearchedUsername()).startsWith("Yo-"));

    }

    @Test
    public void existsByOwner_IdAndSearchedUsernameIgnoreCase(){
        boolean isSearchExists_user1 = searchRepository.existsByOwner_IdAndSearchedUsernameIgnoreCase(mockUser1.getId(),"CXZ");
        boolean isSearchExists_user2 = searchRepository.existsByOwner_IdAndSearchedUsernameIgnoreCase(mockUser2.getId(),"A");
        boolean isSearch2Exists_user2 =searchRepository.existsByOwner_IdAndSearchedUsernameIgnoreCase(mockUser2.getId(),"yo-yo");

        assertThat(isSearchExists_user1).isFalse();
        assertThat(isSearchExists_user2).isFalse();
        assertThat(isSearch2Exists_user2).isTrue();

    }
}
