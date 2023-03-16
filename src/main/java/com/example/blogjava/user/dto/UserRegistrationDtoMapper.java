package com.example.blogjava.user.dto;

import com.example.blogjava.user.User;
import com.example.blogjava.user.UserRole;
import com.example.blogjava.user.repos.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserRegistrationDtoMapper {
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationDtoMapper(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User map(UserRegistrationDto dto){
        final String USER_ROE = "USER";
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        UserRole userRole = userRoleRepository.findUserRoleByRoleName(USER_ROE).orElseThrow(() -> new NoSuchElementException());
        user.getUserRoles().add(userRole);
        return user;
    }
}
