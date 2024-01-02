package com.example.blogjava.web;

import com.example.blogjava.crypto.CoinApiService;
import com.example.blogjava.crypto.CoinDto;
import com.example.blogjava.crypto.CoinService;
import com.example.blogjava.post.PostService;
import com.example.blogjava.post.dto.PostDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;


@Controller
public class HomeController {
    private final PostService postService;
    private final CoinApiService coinApiService;
    private final CoinService coinService;
    int currentPage = 0;
    int pageSize = 3;
    public HomeController(PostService postService, CoinApiService coinApiService, CoinService coinService){
        this.postService = postService;
        this.coinApiService = coinApiService;
        this.coinService = coinService;
    }

    @GetMapping("/")
    public String posts(Model model){
        Page<PostDto> pageOfPosts = getPostPage(model);
        model.addAttribute("coin", new CoinDto());
        model.addAttribute("posts", pageOfPosts);
        model.addAttribute("current_page", currentPage);
//        model.addAttribute("coinsPrices", coinService.getBaseCoinPrices());
        model.addAttribute("coinsPrices", new HashMap<>());
        return "index";
    }
    @PostMapping("/add-coin")
    public String addCoin(@Valid @ModelAttribute("coin") CoinDto coinDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            Page<PostDto> pr = getPostPage(model);
            model.addAttribute("posts", pr);
            return "index";
        }
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

    private Page<PostDto> getPostPage(Model model) {
        PageRequest pr = PageRequest.of(currentPage, pageSize);
        Page<PostDto> pageOfPosts = postService.getPageOfPosts(pr);
        if (pageOfPosts.isLast()) {
            model.addAttribute("isLast", true);
        }
        return pageOfPosts;
    }

}
