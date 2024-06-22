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
            createAdminUser();
            createParkingOperatorUser();
        };
    }

    private void createAdminUser() {
        String adminUsername = "admin";
        if (!userService.userExists(adminUsername)) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("adminpass"))
                    .firstName("Admin")
                    .lastName("User")
                    .iin(1234567890)
                    .roles("ROLE_ADMIN")
                    .enabled(true)
                    .build();
            userService.create(admin);
            System.out.println("Admin user created: " + adminUsername);
        } else {
            System.out.println("Admin user already exists: " + adminUsername);
        }
    }

    private void createParkingOperatorUser() {
        String operatorUsername = "operator";
        if (!userService.userExists(operatorUsername)) {
            User operator = User.builder()
                    .username(operatorUsername)
                    .email("operator@example.com")
                    .password(passwordEncoder.encode("operatorpass"))
                    .firstName("Operator")
                    .lastName("User")
                    .iin(1234567891)
                    .roles("ROLE_PARKING_OPERATOR")
                    .enabled(true)
                    .build();
            userService.create(operator);
            System.out.println("Parking operator user created: " + operatorUsername);
        } else {
            System.out.println("Parking operator user already exists: " + operatorUsername);
        }
    }
}
