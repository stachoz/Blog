package com.example.blogjava.user.dto;

import com.example.blogjava.user.User;
import com.example.blogjava.user.UserRole;
import com.example.blogjava.user.repos.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserRegistrationDtoMapper {
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationDtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User map(UserRegistrationDto dto){
        return new User(
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                false
        );
    }
}
