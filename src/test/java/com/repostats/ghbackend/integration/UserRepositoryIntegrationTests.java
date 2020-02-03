package com.repostats.ghbackend.integration;

import com.repostats.ghbackend.entity.AuthProvider;
import com.repostats.ghbackend.entity.User;
import com.repostats.ghbackend.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@ActiveProfiles("test")
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByEmail() {
        User mockUser = new User();
        mockUser.setEmail("email@email.com");
        mockUser.setUsername("username");
        mockUser.setProvider(AuthProvider.github);
        mockUser.setGithubAccessToken("123");

        User mockUser1 = new User();
        mockUser1.setEmail("email1@email.com");
        mockUser1.setUsername("username1");
        mockUser1.setProvider(AuthProvider.github);
        mockUser1.setGithubAccessToken("321");

        userRepository.save(mockUser);
        userRepository.save(mockUser1);

        assertThat(userRepository.findByEmail("email@email.com").get().getEmail()).isSameAs(mockUser.getEmail());
        assertThat(userRepository.findByEmail("email1@email.com").get().getEmail()).isSameAs(mockUser1.getEmail());

    }

}
