package com.starterkit.backend.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starterkit.backend.user.dto.UserRequestByIdDto;
import com.starterkit.backend.user.dto.UserRequestDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.dto.UserUpdateRequestDto;
import com.starterkit.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto registerDto) {
        UserResponseDto user = userService.register(registerDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> users = userService.getAll();
        
        return ResponseEntity.ok(users);
    }

    @PostMapping("/getById")
    public ResponseEntity<UserResponseDto> getById(@RequestBody UserRequestByIdDto userId) {
        UserResponseDto user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateRequestDto userDto) {
        UserResponseDto updatedUser = userService.update(userDto);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserResponseDto> delete(@RequestBody UserRequestByIdDto userId) {
        UserResponseDto deletedUser = userService.delete(userId);
        if (deletedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedUser);
    }
}