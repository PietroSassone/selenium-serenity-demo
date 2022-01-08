package com.example.selenium.serenity.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration
public class UITestSpringConfig {

    @Bean
    public Faker testDataGenerator() {
        return new Faker();
    }
}
