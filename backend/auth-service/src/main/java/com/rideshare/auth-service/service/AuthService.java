package com.rideshare.authservice.service;

import com.rideshare.authservice.config.JwtUtil;
import com.rideshare.authservice.dto.*;
import com.rideshare.authservice.model.UserAuth;
import com.rideshare.authservice.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        UserAuth user = UserAuth.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        UserAuth user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }
}
