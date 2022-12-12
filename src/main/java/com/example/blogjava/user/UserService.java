package com.example.blogjava.user;

import com.example.blogjava.user.dto.UserCredentialsDto;
import com.example.blogjava.user.dto.UserCredentialsDtoMapper;
import com.example.blogjava.user.dto.UserDto;
import com.example.blogjava.user.dto.UserDtoMapper;
import com.example.blogjava.user.repos.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final String USER_ROLE = "USER";
    private final String ADMIN_ROLE = "ADMIN";

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<UserCredentialsDto> findCredentialsByUsername(String username){
        return userRepository.findByUsername(username).map(
                UserCredentialsDtoMapper::map
        );
    }

    public List<UserDto> findUsersInformation(){
        return userRepository.findAllByUserRoles_RoleName(USER_ROLE).stream()
                .map(UserDtoMapper::map)
                .collect(Collectors.toList());
    }

    public UserDto findUserInformationByName(String username){
        return userRepository.findByUsername(username)
                .map(UserDtoMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
    }

    private boolean isCurrentUserAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_ROLE));
    }

}
