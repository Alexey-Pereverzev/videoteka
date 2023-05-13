package ru.gb.cabinetorderservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfigRest {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
