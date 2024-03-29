package com.example.blogjava.web;

import com.example.blogjava.post.PostService;
import com.example.blogjava.post.report.ReportAdminDto;
import com.example.blogjava.post.report.ReportService;
import com.example.blogjava.user.UserService;
import com.example.blogjava.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final UserService userService;
    private final PostService postService;
    private final ReportService reportService;
    private final String BLOCKED_USER_ROLE = "BLOCKED_USER";

    public AdminPageController(UserService userService, PostService postService, ReportService reportService){
        this.userService = userService;
        this.postService = postService;
        this.reportService = reportService;
    }
    @GetMapping("")
    String admin(Model model){
        List<UserDto> blockedUsersInformation = userService.findUsersInformation(BLOCKED_USER_ROLE);
        List<ReportAdminDto> reports = postService.getAllReports();
        model.addAttribute("blockedUsers", blockedUsersInformation);
        model.addAttribute("reports", reports);
        model.addAttribute("postsToVerification", postService.getPostsToVerification());
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

    @GetMapping("/block-user")
    String blockUser(@RequestParam Long userId, RedirectAttributes redirectAttributes){
        userService.blockUser(userId);
        String username = userService.getUserUsername(userId);
        redirectAttributes.addFlashAttribute("message", username + " is blocked");
        return "redirect:/admin";
    }

    @GetMapping("/unblock-user")
    String unblockUser(@RequestParam String username, RedirectAttributes redirectAttributes){
        userService.unblockUser(username);
        redirectAttributes.addFlashAttribute("message", username + " is unblocked");
        return "redirect:/admin";
    }
    @GetMapping("/delete-report")
    String deleteReport(@RequestParam Long reportId){
        reportService.deleteReport(reportId);
        return "redirect:/admin";
    }

    @GetMapping("/enable-post-verification")
    String enablePostVerification(@RequestParam String username, RedirectAttributes redirectAttributes){
        userService.enablePostVerification(username);
        redirectAttributes.addFlashAttribute("message", "post verification enabled for: " + username);
        return "redirect:/admin";
    }

    @GetMapping("/toVerify")
    String getPostToVerify(@RequestParam Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        return "post/post-to-verify";
    }

    @GetMapping("/toVerify/verified")
    String verifyPost(@RequestParam Long id, @RequestParam boolean status){
        postService.updateVerificationStatus(id, status);
        return "redirect:/admin";
    }
}