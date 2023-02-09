package com.example.blogjava.web;

import com.example.blogjava.post.Post;
import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import com.example.blogjava.post.dto.PostFormDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/add")
    String addPostForm(Model model){
         model.addAttribute("post", new PostFormDto());
         return "add-post-form";
    }
    @PostMapping("/save")
    String savePost(@Valid @ModelAttribute("post") PostFormDto postFormDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "add-post-form";
        }
        postService.savePost(postFormDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    String postPage(@PathVariable Long id, Model model){
        PostDto postById = postService.getPostById(id);
        model.addAttribute("post", postById);
        return "post";
    }
}
