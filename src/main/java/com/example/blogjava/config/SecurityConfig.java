package com.example.blogjava.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String ADMIN_ROLE = "ADMIN";
    private final String USER_ROLE = "USER";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/styles/**").permitAll()
                .requestMatchers("/", "/next_page", "/previous_page").permitAll()
                .requestMatchers("/delete/**").hasRole(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/add-coin").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .requestMatchers("/admin/**").hasRole(ADMIN_ROLE)
                .requestMatchers("/profile").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/post/save", "/post/add").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                .requestMatchers("/post/*").permitAll()
                .requestMatchers("/post/**").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                .anyRequest().denyAll()
        );
        http.formLogin(login -> login
                .loginPage("/login")
                .permitAll());
        http.logout().logoutSuccessUrl("/");
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
