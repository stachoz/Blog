package com.example.blogjava.web;

import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import com.example.blogjava.post.dto.PostFormDto;
import com.example.blogjava.post.post_comment.CommentDto;
import com.example.blogjava.post.post_comment.CommentFormDto;
import com.example.blogjava.post.report.ReportFormDto;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final static String TINY_API_KEY = System.getenv("TINY_API_KEY");
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/add")
    String addPostForm(Model model){
         model.addAttribute("post", new PostFormDto());
         model.addAttribute("tinyApiKey", TINY_API_KEY);
         return "post/add-post-form";
    }
    @PostMapping("/save")
    String savePost(@Valid @ModelAttribute("post") PostFormDto postFormDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "post/add-post-form";
        }
        postService.savePost(postFormDto);
        return "redirect:/";
    }

    @GetMapping("/{postId}")
    String postPage(@PathVariable Long postId, Model model){
        PostDto postById = postService.getPostById(postId);
        List<CommentDto> postComments = postService.getPostComments(postId);
        model.addAttribute("post", postById);
        model.addAttribute("commentForm", new CommentFormDto());
        model.addAttribute("comments", postComments);
        return "post/post";
    }

    @PostMapping("/{postId}/saveComment")
    String saveComment(@Valid @ModelAttribute CommentFormDto commentFormDto, @NotNull BindingResult bindingResult,
                       @PathVariable Long postId){
        if (bindingResult.hasErrors()) {
            return "redirect:/post/" + postId;
        }
        postService.saveComment(commentFormDto, postId);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/{postId}/delete")
    String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "redirect:/";
    }

    @GetMapping("/{postId}/report")
    String reportPost(@PathVariable Long postId, Model model){
        model.addAttribute("reportForm", new ReportFormDto());
        model.addAttribute("reportedPostId", postId);
        return "post/post-report";
    }

    @PostMapping("{postId}/saveReport")
    String saveReport(@ModelAttribute ReportFormDto reportFormDto, @PathVariable Long postId){
        postService.saveReport(reportFormDto, postId);
        return "redirect:report-confirmation";
    }

    @GetMapping("/{postId}/report-confirmation")
    String reportConfirmation(@PathVariable String postId, Model model){
        model.addAttribute("postId", postId);
        return "post/report-confirmation";
    }
}