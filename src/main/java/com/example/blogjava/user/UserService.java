package com.example.blogjava;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

     public Optional<UserCredentialsDto> findCredentialsByUsername(String username){
        Optional<UserCredentialsDto> userCredentialsDto = userRepository.findByUsername(username)
                .map(UserCredentialsDtoMapper::mapper);
        return userCredentialsDto;
    }

}
