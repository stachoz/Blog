package com.example.blogjava;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    String home(){
        return "index";
    }
}
