package com.example.blogjava.user;

import com.example.blogjava.user.dto.*;
import com.example.blogjava.user.repos.UserRepository;
import com.example.blogjava.user.repos.UserRoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String USER_ROLE = "USER";
    private final String ADMIN_AUTHORITY = "ROLE_ADMIN";

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
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

    @Transactional
    public void deleteUserByUsername(String username){
        if(isCurrentUserAdmin()){
            userRepository.deleteByUsername(username);
        }
    }

    @Transactional
    public void registerUser(UserRegistrationDto dto){
        if (isUserUnique(dto)){
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            Optional<UserRole> userRole = userRoleRepository.findUserRoleByRoleName(USER_ROLE);
            userRole.ifPresentOrElse(
                    role -> user.getUserRoles().add(role),
                    () -> {
                        throw new NoSuchElementException();
                    }
            );
            userRepository.save(user);
        }
    }

    private boolean isCurrentUserAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
    }

    private boolean isUserUnique(UserRegistrationDto userRegistrationDto){
        String email = userRegistrationDto.getEmail();
        String username = userRegistrationDto.getUsername();
        Optional<User> byEmail = userRepository.findByEmail(email);
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byEmail.isPresent() && byUsername.isPresent()){
            return false;
        }
        return true;
    }

}
