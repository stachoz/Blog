package com.example.blogjava.dtos;

import com.example.blogjava.models.User;

public class UserCredentialsDtoMapper {
    UserCredentialsDto mapper(User user){
        UserCredentialsDto dto = new UserCredentialsDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
