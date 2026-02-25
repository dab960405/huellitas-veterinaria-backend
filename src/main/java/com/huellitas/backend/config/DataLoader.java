package com.huellitas.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("======================================");
            System.out.println("  Backend Huellitas listo para usar  ");
            System.out.println("  http://localhost:8080/api/health   ");
            System.out.println("======================================");
        };
    }
}
