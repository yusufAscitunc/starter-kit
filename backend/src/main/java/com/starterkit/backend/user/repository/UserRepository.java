package com.starterkit.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starterkit.backend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
} 
