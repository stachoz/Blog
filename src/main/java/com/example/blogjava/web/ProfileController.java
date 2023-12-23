package com.example.blogjava.web;

import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    String profile(@RequestParam String username, Model model){
        UserDto userDto = userService.findUserInformationByName(username);
        model.addAttribute("user", userDto);
        model.addAttribute("postsCount", userService.countPosts());
        return "profile";
    }

}
