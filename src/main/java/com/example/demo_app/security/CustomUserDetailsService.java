package com.example.demo_app.security;

import com.example.demo_app.auth.entity.RoleEntity;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.auth.exception.UserNotFoundException;
import com.example.demo_app.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = email.trim().toLowerCase();
        UserEntity user = userRepository.findUserByEmail(normalizedEmail).orElseThrow(()-> new UserNotFoundException());
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
            r.getPermissions().forEach(permission ->
                    authorities.add(
                            new SimpleGrantedAuthority(permission.getName())
                    )
            );
        }
        return User.withUsername(normalizedEmail)
                .authorities(authorities)
                .password(user.getPasswordHash())
                .build();
    }
}
