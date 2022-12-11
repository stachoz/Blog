package com.example.blogjava;

import com.example.blogjava.user.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
