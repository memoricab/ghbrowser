package com.repostats.ghbackend.config;

import com.repostats.ghbackend.handler.RestTemplateResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private RestTemplateResponseHandler restTemplateResponseHandler;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.errorHandler(restTemplateResponseHandler)
                .build();
    }
}
