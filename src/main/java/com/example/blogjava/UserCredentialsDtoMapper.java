package com.example.blogjava;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper {
    public static UserCredentialsDto mapper(HomeController.User user){
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        Set<String> userRoles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());
        return new UserCredentialsDto(username, password, userRoles);
    }
}
