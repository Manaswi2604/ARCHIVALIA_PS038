package com.archivalia.auth.config;

import com.archivalia.auth.model.AppUser;
import com.archivalia.auth.model.ERole;
import com.archivalia.auth.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (repo.findByEmail("user@archivalia.com").isEmpty()) {
                repo.save(AppUser.builder()
                        .email("user@archivalia.com")
                        .password(encoder.encode("user123"))
                        .role(ERole.ROLE_USER)
                        .build());
            }

            if (repo.findByEmail("admin@archivalia.com").isEmpty()) {
                repo.save(AppUser.builder()
                        .email("admin@archivalia.com")
                        .password(encoder.encode("admin123"))
                        .role(ERole.ROLE_ADMIN)
                        .build());
            }
        };
    }
}