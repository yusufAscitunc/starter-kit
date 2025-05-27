package com.starterkit.backend.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.starterkit.backend.user.dto.UserRequestByIdDto;
import com.starterkit.backend.user.dto.UserRequestDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.dto.UserUpdateRequestDto;
import com.starterkit.backend.user.entity.User;
import com.starterkit.backend.user.mapper.UserMapper;
import com.starterkit.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(UserRequestDto registerDto) {
        User user = userMapper.toEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(user.getRole() != null ? user.getRole() : "USER"); // Default role if not set
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public List<UserResponseDto> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return List.of();
        }
        return userMapper.toDtoList(users);
    }

    public UserResponseDto getById(UserRequestByIdDto userId) {
        User user = userRepository.findById(userId.getId()).orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.toDto(user);
    }

    public UserResponseDto update(UserUpdateRequestDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElse(null);
        if (user == null || !userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found");
        }
        user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail()); // Keep existing email if not set
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRole(userDto.getRole() != null ? userDto.getRole() : user.getRole()); // Keep existing role if not set
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public UserResponseDto delete(UserRequestByIdDto userId) {
        User user = userRepository.findById(userId.getId()).orElse(null);
        if (user == null) {
            return null;
        }
        userRepository.delete(user);
        return userMapper.toDto(user);
    }
}
