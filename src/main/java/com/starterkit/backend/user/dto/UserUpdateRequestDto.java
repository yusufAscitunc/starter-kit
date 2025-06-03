package com.starterkit.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequestDto {
    @NotNull(message = "User ID cannot be null")
    private Long id;
    private String email;
    private String password;
    private String role;
}
