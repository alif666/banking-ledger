package com.example.demo_app.auth.dto;

import com.example.demo_app.auth.entity.RoleEntity;

import java.time.Instant;
import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String email,
        Instant createdAt,
        List<String> roles
) {
}
