package com.example.blogjava.user;

import com.example.blogjava.HomeController;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {
    public static UserDto map(HomeController.User user){
        String username = user.getUsername();
        String email = user.getEmail();
        Set<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());
        return new UserDto(username,email, roles);
    }
}
