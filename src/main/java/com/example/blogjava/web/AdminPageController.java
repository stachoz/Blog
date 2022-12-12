package com.example.blogjava.web;

import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final UserService userService;

    public AdminPageController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("")
    String admin(Model model){
        List<UserDto> usersInformation = userService.findUsersInformation();
        model.addAttribute("users", usersInformation);
        return "admin";
    }
}
