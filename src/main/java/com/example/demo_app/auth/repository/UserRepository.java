package com.example.demo_app.auth.repository;

import com.example.demo_app.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    @Query("""
        select distinct u
        from UserEntity u
        left join fetch u.roles r
        left join fetch r.permissions
        where u.email=:email

    """)
    Optional<UserEntity> findUserByEmail(@Param("email") String email);
}
