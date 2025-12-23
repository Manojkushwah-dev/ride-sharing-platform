package com.rideshare.authservice.controller;

import com.rideshare.authservice.dto.AuthResponse;
import com.rideshare.authservice.dto.LoginRequest;
import com.rideshare.authservice.dto.RegisterRequest;
import com.rideshare.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        // Service now handles everything including token generation
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // Service now handles everything including token generation
        return authService.login(request);
    }
}