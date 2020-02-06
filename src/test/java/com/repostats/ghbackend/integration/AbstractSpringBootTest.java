package com.repostats.ghbackend.integration;

import com.repostats.ghbackend.GhbackendApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = GhbackendApplication.class)
@AutoConfigureMockMvc
public class AbstractSpringBootTest {

    @Autowired
    protected MockMvc mockMvc;
}
