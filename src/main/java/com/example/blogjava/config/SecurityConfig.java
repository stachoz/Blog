package com.example.blogjava.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(h2ConsoleRequestMatcher).permitAll()
                .requestMatchers("/styles/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin-page/").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/login")
                .permitAll());
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        return http.build();
    }
}