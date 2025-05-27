package com.starterkit.backend.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String password;
    private String role;
}