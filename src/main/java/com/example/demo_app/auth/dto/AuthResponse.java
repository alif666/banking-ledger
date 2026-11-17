package com.example.demo_app.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
}
