package com.example.blogjava;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }
    @GetMapping("/profile")
    String profile(Model model, Authentication authentication){
        String username = authentication.getName();
        Optional<UserDto> userAccountInformation = profileService.findUserAccountInformation(username);
        userAccountInformation.ifPresent(
                userDto -> model.addAttribute("userAccountInformation", userDto)
        );
        return "profile";
    }
}
