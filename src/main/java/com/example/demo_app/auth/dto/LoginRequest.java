package com.example.demo_app.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        @Size(max=50, message = "Size should be less than 50")
        String email,
        @NotBlank(message = "Password shouldn't be blank")
        @Size(min=8, max=50, message="Size should be less than 50")
        String password
) {
}
