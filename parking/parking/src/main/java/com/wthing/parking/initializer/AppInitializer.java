package com.wthing.parking.initializer;

import com.wthing.parking.models.User;
import com.wthing.parking.services.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppInitializer {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            String adminUsername = "admin";
            if (!userService.userExists(adminUsername)) {
                User admin = User.builder()
                        .username(adminUsername)
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("adminpass"))
                        .firstName("Maximiliano")
                        .lastName("Lecstapperc")
                        .iin(1234567890)
                        .roles("ROLE_ADMIN")
                        .enabled(true)
                        .build();
                userService.create(admin);
                System.out.println("Admin user created: " + adminUsername);
            } else {
                System.out.println("Admin user already exists: " + adminUsername);
            }
        };
    }
}
