package com.example.demo_app.auth.service;

import com.example.demo_app.auth.entity.LoginAttempt;

public interface LoginAttemptService {
    LoginAttempt createLoginAttempt(LoginAttempt loginAttempt);
    LoginAttempt updateStatus(Long id);
}
