package com.example.blogjava;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<UserDto> findUserAccountInformation(String username){
        Optional<UserDto> userDto = userRepository.findByUsername(username).map(UserDtoMapper::map);
        return userDto;
    }
}
