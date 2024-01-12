package com.example.blogjava.web;

import com.example.blogjava.crypto.CoinService;
import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final CoinService coinService;

    public ProfileController(UserService userService, CoinService coinService){
        this.userService = userService;
        this.coinService = coinService;
    }

    @GetMapping("")
    String profile(@RequestParam String username, Model model){
        UserDto userDto = userService.findUserInformationByName(username);
        model.addAttribute("user", userDto);
        model.addAttribute("postsCount", userService.countPosts());
        return "profile";
    }
}
