package com.example.blogjava.web;

import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final PostService postService;
    public HomeController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/")
    public String posts(@RequestParam(defaultValue = "0") int page, Model model){
        int pageSize = 3;
        PageRequest pr = PageRequest.of(page, pageSize);
        Page<PostDto> pageOfPosts = postService.getPageOfPosts(pr);
        model.addAttribute("posts", pageOfPosts) ;
        return "index";
    }
}
