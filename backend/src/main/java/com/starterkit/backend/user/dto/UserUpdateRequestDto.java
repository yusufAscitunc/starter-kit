package com.starterkit.backend.user.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private Long id;
    private String email;
    private String password;
    private String role;
}
