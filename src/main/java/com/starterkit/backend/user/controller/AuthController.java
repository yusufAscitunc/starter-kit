package com.starterkit.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starterkit.backend.user.dto.UserLoginDto;
import com.starterkit.backend.user.dto.UserRequestDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto registerDto) {
        UserResponseDto registeredUser = authService.register(registerDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody UserLoginDto userLoginDto) {
        String jwt = authService.login(userLoginDto);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}

class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
