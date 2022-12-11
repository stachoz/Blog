package com.example.blogjava;

import com.example.blogjava.config.CustomUserDetailsService;
import jakarta.persistence.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @GetMapping("/")
    String home(){
        return "index";
    }

    @Entity
    @Table(name = "application_user")
    public static class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String email;
        private String password;
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<CustomUserDetailsService.UserRole> userRoles = new HashSet<>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Set<CustomUserDetailsService.UserRole> getUserRoles() {
            return userRoles;
        }

        public void setUserRoles(Set<CustomUserDetailsService.UserRole> userRoles) {
            this.userRoles = userRoles;
        }
    }
}
