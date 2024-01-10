package com.example.blogjava.web;

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

@Controller
public class HomeController {
    private final PostService postService;
    private final CoinService coinService;
    int currentPage = 0;
    int pageSize = 3;
    public HomeController(PostService postService, CoinService coinService){
        this.postService = postService;
        this.coinService = coinService;
    }

    @GetMapping("/")
    public String posts(Model model){
        model.addAttribute("coin", new CoinDto());
        loadHomeData(model);
        return "index";
    }
    @PostMapping("/add-coin")
    public String addCoin(@Valid @ModelAttribute("coin") CoinDto coinDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            loadHomeData(model);
            return "index";
        }
        coinService.addNewCoin(coinDto.getName());
        return "redirect:/";
    }

    @GetMapping("/next_page")
    public String nextPage(){
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

    private void loadHomeData(Model model){
        Page<PostDto> pr = getPostPage(model);
        model.addAttribute("posts", pr);
        model.addAttribute("current_page", currentPage);
        model.addAttribute("coinsPrices", coinService.getBaseCoinPrices());
        model.addAttribute("userCoinsPrices", coinService.getUserCoins());
    }
}
