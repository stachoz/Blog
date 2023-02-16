package com.example.blogjava.web;

import com.example.blogjava.post.PostService;
import com.example.blogjava.post.report.ReportAdminDto;
import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final UserService userService;
    private final PostService postService;

    public AdminPageController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }
    @GetMapping("")
    String admin(Model model){
        List<UserDto> usersInformation = userService.findUsersInformation();
        List<ReportAdminDto> reports = postService.getAllReports();
        model.addAttribute("users", usersInformation);
        model.addAttribute("reports", reports);
        return "admin";
    }

    @GetMapping("/delete-user")
    String deleteUser(@RequestParam String username){
        userService.deleteUserByUsername(username);
        return "redirect:/admin";
    }

    @GetMapping("/delete-post")
    String deletePost(@RequestParam Long postId){
        postService.deletePost(postId);
        return "redirect:/admin";
    }
}