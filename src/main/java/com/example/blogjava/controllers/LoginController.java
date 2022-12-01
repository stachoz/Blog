package com.example.blogjava.controllers;

import com.example.blogjava.dtos.UserCredentialsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login(){
        return "login-page";
    }
}
