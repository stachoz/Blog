package com.example.blogjava.config;

import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserCredentialsDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userService.findCredentialsByUsername(username)
               .map(dto -> createUserDetails(dto))
               .orElseThrow(() -> new UsernameNotFoundException(String.format("Username with email %s not foung", username)));
    }

    private UserDetails createUserDetails(UserCredentialsDto dto){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles().toArray(String[]::new))
                .build();
    }
}
