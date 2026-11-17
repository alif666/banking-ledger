package com.example.demo_app.auth.service.impl;

import com.example.demo_app.auth.dto.AuthResponse;
import com.example.demo_app.auth.dto.LoginRequest;
import com.example.demo_app.auth.dto.RegisterRequest;
import com.example.demo_app.auth.entity.LoginAttempt;
import com.example.demo_app.auth.entity.LoginAttemptStatus;
import com.example.demo_app.auth.entity.RoleEntity;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.auth.exception.DefaultRoleNotFoundException;
import com.example.demo_app.auth.exception.EmailAlreadyRegisteredException;
import com.example.demo_app.auth.exception.UserNotFoundException;
import com.example.demo_app.auth.repository.RoleRepository;
import com.example.demo_app.auth.repository.UserRepository;
import com.example.demo_app.auth.service.AuthService;
import com.example.demo_app.auth.service.LoginAttemptService;
import com.example.demo_app.auth.service.RoleService;
import com.example.demo_app.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final RoleService roleService;
    private final LoginAttemptService loginAttemptService;

    @Transactional
    @Override
    public UserEntity register(RegisterRequest registerRequest) {
        String normalizedEmail = registerRequest.email().trim().toLowerCase();


        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyRegisteredException();
        }
        UserEntity user = new UserEntity();
        user.setEmail(normalizedEmail);
        user.setUsername(registerRequest.username());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));

        try {
            UserEntity savedUser = userRepository.save(user);
            roleService.assignDefaultRole(savedUser.getId());
            return savedUser;
        } catch (DataIntegrityViolationException ex) {
            throw new EmailAlreadyRegisteredException();
        }
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        LoginAttempt loginAttempt = new LoginAttempt();
        loginAttempt.setEmail(loginRequest.email());
        loginAttempt.setStatus(LoginAttemptStatus.FAILURE);
        LoginAttempt savedLoginAttempt = loginAttemptService.createLoginAttempt(loginAttempt);


        Authentication authentication = authenticate(loginRequest);
        loginAttemptService.updateStatus(savedLoginAttempt.getId());
        String accessToken = jwtTokenService.createAccessToken(authentication);
        return new AuthResponse(accessToken, "Bearer");
    }


    public Authentication authenticate(LoginRequest loginRequest) {
        String normalizedEmail = loginRequest.email().trim().toLowerCase();
        String password = loginRequest.password();
        Authentication unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(normalizedEmail, password);
        return authenticationManager.authenticate(unauthenticated);
    }

}
