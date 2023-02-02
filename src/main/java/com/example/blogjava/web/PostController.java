package com.example.blogjava.web;

import com.example.blogjava.comment.dto.CommentDto;
import com.example.blogjava.comment.dto.CommentFormDto;
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
        List<CommentDto> allPostComments = postService.getAllComments(id);
        model.addAttribute("comments", allPostComments);
        model.addAttribute("post", postById);
        model.addAttribute("comment", new CommentFormDto());
        return "post";
    }

    @PostMapping("/{id}/save-comment")
    String saveComment(@Valid @ModelAttribute("comment") CommentFormDto commentFormDto,BindingResult bindingResult,
                       @PathVariable(name = "id") Long postId){
        if (bindingResult.hasErrors()){
            return "post";
        }
        postService.saveComment(commentFormDto, postId);
        return "redirect:/post/" + postId;
    }
}
