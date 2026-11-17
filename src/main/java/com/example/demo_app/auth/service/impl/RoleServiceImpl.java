package com.example.demo_app.auth.service.impl;

import com.example.demo_app.auth.entity.RoleEntity;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.auth.exception.UserNotFoundException;
import com.example.demo_app.auth.repository.RoleRepository;
import com.example.demo_app.auth.repository.UserRepository;
import com.example.demo_app.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void assignDefaultRole(Long userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException());
        RoleEntity role = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.getRoles().add(role);
    }
}
