package com.example.blogjava.user.dto;

import com.example.blogjava.user.User;
import com.example.blogjava.web.HomeController;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {
    public static UserDto map(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
