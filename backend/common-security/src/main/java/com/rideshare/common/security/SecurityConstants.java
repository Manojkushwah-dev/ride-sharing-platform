package com.rideshare.common.security;

public class SecurityConstants {
    // This MUST match across all services
    public static final String JWT_SECRET = "rideshare-secret-key-rideshare-secret-key-must-be-long";
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

    private SecurityConstants() {}
}