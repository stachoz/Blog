package com.example.blogjava.user.dto;

import com.example.blogjava.user.User;
import com.example.blogjava.web.HomeController;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {
    public static UserDto map(User user){
        String username = user.getUsername();
        String email = user.getEmail();
        return new UserDto(username,email);
    }
}
