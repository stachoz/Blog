package com.example.blogjava.user;

import com.example.blogjava.user.dto.*;
import com.example.blogjava.user.repos.UserRepository;
import com.example.blogjava.user.repos.UserRoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRegistrationDtoMapper userRegistrationDtoMapper;
    private final String USER_ROLE = "USER";
    private final String BLOCKED_USER_ROLE = "BLOCKED_USER";
    private final String ADMIN_AUTHORITY = "ROLE_ADMIN";

    public UserService(UserRepository userRepository,UserRoleRepository userRoleRepository, UserRegistrationDtoMapper userRegistrationDtoMapper){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRegistrationDtoMapper = userRegistrationDtoMapper;
    }

    public Optional<UserCredentialsDto> findCredentialsByUsername(String username){
        return userRepository.findByUsername(username).map(
                UserCredentialsDtoMapper::map
        );
    }

    public List<UserDto> findUsersInformation(String userRole){
        return userRepository.findAllByUserRoles_RoleName(userRole).stream()
                .map(UserDtoMapper::map)
                .collect(Collectors.toList());
    }

    public UserDto findUserInformationByName(String username){
        return userRepository.findByUsername(username)
                .map(UserDtoMapper::map)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
    }

    public String getUserUsername(Long userId){
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return user.getUsername();
    }

    @Transactional
    public void deleteUserByUsername(String username){
        if(isCurrentUserAdmin()){
            userRepository.deleteByUsername(username);
        }
    }

    @Transactional
    public void registerUser(UserRegistrationDto dto){
        User user = userRegistrationDtoMapper.map(dto);
        try{
            userRepository.save(user);
        } catch (ConstraintViolationException cve){
            Set<ConstraintViolation<?>> constraintViolations = cve.getConstraintViolations();
            System.err.println("Constraint violations for object " + user);
            constraintViolations.forEach(System.err::println);
        }
    }
    @Transactional
    public boolean isEmailUnique(String email){
        return !userRepository.existsUserByEmail(email);
    }

    @Transactional
    public boolean isUsernameUnique(String username){
        return !userRepository.existsUserByUsername(username);
    }

    @Transactional
    public void blockUser(Long userId){
        if (isCurrentUserAdmin()){
            User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
            UserRole userRole = userRoleRepository.findUserRoleByRoleName(USER_ROLE).orElseThrow(NoSuchElementException::new);
            UserRole blockedUserRole = userRoleRepository.findUserRoleByRoleName(BLOCKED_USER_ROLE).orElseThrow(NoSuchElementException::new);
            user.getUserRoles().remove(userRole);
            user.getUserRoles().add(blockedUserRole);
        }
    }

    @Transactional
    public void unblockUser(String username){
        if(isCurrentUserAdmin()){
            User user= userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
            UserRole blockedUserRole = userRoleRepository.findUserRoleByRoleName(BLOCKED_USER_ROLE).orElseThrow(NoSuchElementException::new);
            UserRole userRole = userRoleRepository.findUserRoleByRoleName(USER_ROLE).orElseThrow(NoSuchElementException::new);
            user.getUserRoles().remove(blockedUserRole);
            user.getUserRoles().add(userRole);
        }
    }

    private boolean isCurrentUserAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
    }


}
