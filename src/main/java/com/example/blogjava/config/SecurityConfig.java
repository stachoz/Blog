package com.example.blogjava.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final String USER_ROLE = "USER";
    private final String BLOCKED_USER_ROLE = "BLOCKED_USER";
    private final String ADMIN_ROLE = "ADMIN";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(h2ConsoleRequestMatcher).permitAll()
                .requestMatchers("/styles/**").permitAll()
                .requestMatchers("/", "/next_page", "/previous_page").permitAll()
                .requestMatchers("/admin/**").hasRole(ADMIN_ROLE)
                .requestMatchers("/profile/**").hasRole(USER_ROLE)
                .requestMatchers(HttpMethod.GET,"/post/*").permitAll()
                .requestMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/login")
                .permitAll());
        http.logout().logoutSuccessUrl("/");
        http.formLogin().defaultSuccessUrl("/");
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
