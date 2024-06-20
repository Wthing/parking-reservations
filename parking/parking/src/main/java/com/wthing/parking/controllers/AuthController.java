package com.wthing.parking.controllers;

import com.wthing.parking.dto.auth.SignInRequest;
import com.wthing.parking.dto.auth.SignUpRequest;
import com.wthing.parking.services.jwtauth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public String signUp(@RequestBody @Valid SignUpRequest request) {
        authenticationService.signUp(request);
        return "redirect";
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid SignInRequest request) {
        authenticationService.signIn(request);
        return "redirect";
    }
}