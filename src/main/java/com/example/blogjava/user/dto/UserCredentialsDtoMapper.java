package com.example.blogjava.user.dto;

import com.example.blogjava.user.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {
    public static UserCredentialsDto map(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        Set<String> userRoles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());
        return new UserCredentialsDto(username, password, userRoles);
    }
}
