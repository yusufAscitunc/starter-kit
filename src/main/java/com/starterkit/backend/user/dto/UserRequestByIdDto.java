package com.starterkit.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestByIdDto {
    @NotNull(message = "User ID cannot be null")
    private Long id;
}
