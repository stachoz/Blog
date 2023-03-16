package com.example.blogjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login(Model model){
        String registerMsg= (String)model.asMap().get("register successful");
        model.addAttribute("registerMsg", registerMsg);
        return "login-page";
    }
}
