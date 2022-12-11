package com.example.blogjava;

import com.example.blogjava.user.dto.UserDto;
import com.example.blogjava.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
