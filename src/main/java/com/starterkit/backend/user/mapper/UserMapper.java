package com.starterkit.backend.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.starterkit.backend.user.dto.UserRequestDto;
import com.starterkit.backend.user.dto.UserResponseDto;
import com.starterkit.backend.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);
    UserResponseDto toDto(User user);
    List<UserResponseDto> toDtoList(List<User> users);
}