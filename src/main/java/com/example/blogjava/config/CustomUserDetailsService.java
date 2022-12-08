package com.example.blogjava;

import com.example.blogjava.user.UserCredentialsDto;
import com.example.blogjava.user.UserService;
import jakarta.persistence.*;
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

    @Entity
    @Table(name = "user_role")
    public static class UserRole {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String roleName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String name) {
            this.roleName = name;
        }
    }
}
