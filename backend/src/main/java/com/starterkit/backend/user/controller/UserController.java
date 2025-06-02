package com.starterkit.backend.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starterkit.backend.security.CustomUserPrincipal;
import com.starterkit.backend.user.dto.UserRequestByIdDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.dto.UserUpdateRequestDto;
import com.starterkit.backend.user.mapper.UserMapper;
import com.starterkit.backend.user.service.AuthService;
import com.starterkit.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        UserResponseDto userDto = userMapper.toDto(principal.getUser());
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        if (!authService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/getById")
    public ResponseEntity<UserResponseDto> getById(@RequestBody UserRequestByIdDto userId) {
        if (!authService.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
            UserResponseDto user = userService.getById(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or #userDto.id == authentication.principal.user.id")
    @PostMapping("/update")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateRequestDto userDto, 
        @AuthenticationPrincipal CustomUserPrincipal principal) {
            // Check if user is not an admin and trying to update someone else's account
            if (!authService.isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            UserResponseDto updatedUser = userService.update(userDto);
            if (updatedUser == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<UserResponseDto> delete(@RequestBody UserRequestByIdDto userId, 
        @AuthenticationPrincipal CustomUserPrincipal principal) {
            if (!authService.isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            UserResponseDto deletedUser = userService.delete(userId);
            if (deletedUser == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(deletedUser);
    }
}