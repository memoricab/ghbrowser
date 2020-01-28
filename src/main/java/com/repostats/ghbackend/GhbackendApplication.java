package com.repostats.ghbackend;

import com.repostats.ghbackend.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class GhbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GhbackendApplication.class, args);
    }

}
