package com.example.blogjava.web;

import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    String registerForm(Model model){
        model.addAttribute("user", new UserRegistrationDto());
        return "register-form";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto, BindingResult bindingResult,
                    RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "register-form";
        }

        if (!userService.isEmailUnique(userRegistrationDto.getEmail())){
            bindingResult.rejectValue("email", "", "User with given email address already exists");
            return "register-form";
        }

        if (!userService.isUsernameUnique(userRegistrationDto.getUsername())){
            bindingResult.rejectValue("username", "", "username is already taken");
            return "register-form";
        }
        redirectAttributes.addFlashAttribute("register successful", "you have registered successfully");
        userService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }
}
