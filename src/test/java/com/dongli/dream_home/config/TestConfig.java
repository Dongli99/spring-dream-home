package com.dongli.dream_home.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
