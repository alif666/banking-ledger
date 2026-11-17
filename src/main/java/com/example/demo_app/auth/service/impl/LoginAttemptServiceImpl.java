package com.example.demo_app.auth.service.impl;

import com.example.demo_app.auth.entity.LoginAttempt;
import com.example.demo_app.auth.entity.LoginAttemptStatus;
import com.example.demo_app.auth.repository.LoginAttemptRepository;
import com.example.demo_app.auth.service.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private final LoginAttemptRepository loginAttemptRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public LoginAttempt createLoginAttempt(LoginAttempt loginAttempt) {
        return loginAttemptRepository.save(loginAttempt);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public LoginAttempt updateStatus(Long id){
        LoginAttempt loginAttempt = loginAttemptRepository.findById(id).orElseThrow();
        loginAttempt.setStatus(LoginAttemptStatus.SUCCESS);
        return loginAttemptRepository.save(loginAttempt);
    }
}
