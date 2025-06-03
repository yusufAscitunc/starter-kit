package com.starterkit.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.starterkit.backend.security.CustomUserPrincipal;
import com.starterkit.backend.security.JwtUtil;
import com.starterkit.backend.user.dto.UserLoginDto;
import com.starterkit.backend.user.dto.UserRequestDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.entity.User;
import com.starterkit.backend.user.mapper.UserMapper;
import com.starterkit.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;    
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    public String login(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(),
            loginDto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        return jwtUtil.generateToken(userPrincipal.getUsername());
    }

    public UserResponseDto register(UserRequestDto registerDto) {
        User user = userMapper.toEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(user.getRole() != null ? user.getRole() : "USER"); // Default role if not set
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getRole().equals("ADMIN");
    }

}
