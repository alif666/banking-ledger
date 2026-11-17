package com.example.demo_app.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(max=50)
        String username,
        @Email
        @NotBlank
        @Size(max=50)
        String email,
        @NotBlank
        @Size(min=8, max=100)
        String password
) {

}
