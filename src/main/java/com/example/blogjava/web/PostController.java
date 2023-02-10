package com.example.blogjava.web;

import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import com.example.blogjava.post.dto.PostFormDto;
import com.example.blogjava.post.post_comment.CommentDto;
import com.example.blogjava.post.post_comment.CommentFormDto;
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

    @GetMapping("/{postId}")
    String postPage(@PathVariable Long postId, Model model){
        PostDto postById = postService.getPostById(postId);
        List<CommentDto> postComments = postService.getPostComments(postId);
        model.addAttribute("post", postById);
        model.addAttribute("commentForm", new CommentFormDto());
        model.addAttribute("comments", postComments);
        return "post";
    }

    @PostMapping("/{postId}/saveComment")
    String saveComment(@Valid @ModelAttribute CommentFormDto commentFormDto, BindingResult bindingResult,
                       @PathVariable Long postId){
        if (bindingResult.hasErrors()) {
            return "post";
        }
        postService.saveComment(commentFormDto, postId);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/{postId}/delete")
    String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "redirect:/";
    }
}
