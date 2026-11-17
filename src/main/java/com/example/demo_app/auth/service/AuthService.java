package com.example.demo_app.auth.service;

import com.example.demo_app.auth.dto.AuthResponse;
import com.example.demo_app.auth.dto.LoginRequest;
import com.example.demo_app.auth.dto.RegisterRequest;
import com.example.demo_app.auth.entity.UserEntity;

public interface AuthService {

    UserEntity register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
