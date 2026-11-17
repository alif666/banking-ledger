package com.example.demo_app.auth.controller;


import com.example.demo_app.auth.dto.AuthResponse;
import com.example.demo_app.auth.dto.LoginRequest;
import com.example.demo_app.auth.dto.RegisterRequest;
import com.example.demo_app.auth.dto.UserResponse;
import com.example.demo_app.auth.entity.RoleEntity;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request){
        UserEntity userEntity = service.register(request);
        List<String> roles = userEntity.getRoles().stream().map(RoleEntity::getName).toList();
        UserResponse response = new UserResponse(
          userEntity.getId(),
          userEntity.getUsername(),
          userEntity.getEmail(),
          userEntity.getCreatedAt(),
          roles
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

}
