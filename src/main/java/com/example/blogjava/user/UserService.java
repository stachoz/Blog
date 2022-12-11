package com.example.blogjava.user;

import com.example.blogjava.user.dto.UserCredentialsDto;
import com.example.blogjava.user.dto.UserCredentialsDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


}
