package com.example.blogjava.web;

import com.example.blogjava.crypto.CoinDto;
import com.example.blogjava.crypto.CryptoService;
import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    private final PostService postService;
    private final CryptoService cryptoService;
    int currentPage = 0;
    int pageSize = 3;
    public HomeController(PostService postService, CryptoService cryptoService){
        this.postService = postService;
        this.cryptoService = cryptoService;
    }

    @GetMapping("/")
    public String posts(Model model){
        Page<PostDto> pageOfPosts = getPostPage(model);
        model.addAttribute("coin", new CoinDto());
        model.addAttribute("posts", pageOfPosts);
        model.addAttribute("current_page", currentPage);
        return "index";
    }
    @PostMapping("/add-coin")
    public String addCoin(@Valid @ModelAttribute("coin") CoinDto coinDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            Page<PostDto> pr = getPostPage(model);
            model.addAttribute("posts", pr);
            return "index";
        }
//        if(cryptoService.isSupportedByApi(coinDto.getName())) System.out.println("is supported");
        return "redirect:/";
    }

    @GetMapping("/next_page")
    public String nexPage(){
        currentPage++;
        return "redirect:/";
    }

    @GetMapping("/previous_page")
    public String previousPage(){
        if (currentPage > 0) currentPage--;
        return "redirect:/";
    }

    @NotNull
    private Page<PostDto> getPostPage(Model model) {
        PageRequest pr = PageRequest.of(currentPage, pageSize);
        Page<PostDto> pageOfPosts = postService.getPageOfPosts(pr);
        if (pageOfPosts.isLast()) {
            model.addAttribute("isLast", true);
        }
        return pageOfPosts;
    }

}
