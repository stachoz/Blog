package com.example.blogjava.crypto;

import com.example.blogjava.user.User;
import com.example.blogjava.user.repos.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoinService {
    private final CoinApiService coinApiService;
    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    public CoinService(CoinApiService coinApiService, CoinRepository coinRepository,
                       UserRepository userRepository) {
        this.coinApiService = coinApiService;
        this.coinRepository = coinRepository;
        this.userRepository = userRepository;
    }

    public HashMap<String,BigDecimal> getBaseCoinPrices(){
        HashMap<String, BigDecimal> coinPrice = new LinkedHashMap<>();
        for (BaseCoins baseCoin : BaseCoins.values()) {
            String baseCoinName = baseCoin.name();
            Coin coin = getUpdatedCoin(baseCoinName);
            coinPrice.put(baseCoinName, coin.getCurrentPrice());
        }
        return coinPrice;
    }

    public boolean addNewCoin(String coinName){
        Coin coin = getUpdatedCoin(coinName);
        User currentUser = getCurrentUser();
        if(coin.getCurrentPrice() == null) return false;
        currentUser.addCoin(coin);
        userRepository.save(currentUser);
        return true;
    }

    public Map<String, BigDecimal> getUserCoinsPrice(){
            return getUserCoinRecords().stream().collect(Collectors.toMap(
                    Coin::getName,
                    coin -> updateCoinPrice(coin.getName(), coin.getDateTime()).orElseGet(coin::getCurrentPrice)));
    }

    public void detachCoinFromUser(String coinName){
        User currentUser = getCurrentUser();
        currentUser.getCoins().removeIf(coin -> coin.getName().equals(coinName));
        userRepository.save(currentUser);
    }

    private Set<Coin> getUserCoinRecords() {
        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User user = getCurrentUser();
            return user.getCoins();
        }
        return Set.of();
    }

    private Optional<BigDecimal> getCoinPriceFromApi(String coinName){
        Optional<HashMap<String, String>> coinJSON = coinApiService.getCoinJSON(coinName);
        Map<String, String> coinData = coinJSON.orElseThrow(() -> new RuntimeException("empty coin Json"));
        if(coinData.containsKey("error")) return Optional.empty();
        return Optional.of(new BigDecimal(coinData.get("rate")));
    }
    private Optional<BigDecimal> updateCoinPrice(String coinName, LocalDateTime dateTime){
        int minutesThreshold = 10;
        if(dateTime == null || !dateTime.isAfter(LocalDateTime.now().minusMinutes(minutesThreshold))) {
            return getCoinPriceFromApi(coinName);
        }
        return Optional.empty();
    }

    private Coin getUpdatedCoin(String coinName){
        Coin coin = coinRepository.findCoinByName(coinName).orElseGet(() -> new Coin(coinName));
        updateCoinPrice(coinName, coin.getDateTime()).ifPresent(currentPrice -> {
            coin.setCurrentPrice(currentPrice);
            coinRepository.save(coin);
        });
        return coin;
    }

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return  userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("cannot get user from db"));
    }
}