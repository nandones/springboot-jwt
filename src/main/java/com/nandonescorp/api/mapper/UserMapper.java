package com.nandonescorp.api.mapper;

import com.nandonescorp.api.dto.UserResponseDTO;
import com.nandonescorp.api.model.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername());
    }
}
