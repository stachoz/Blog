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
        for (BaseCoins baseCoins : BaseCoins.values()) {
            String coinName = baseCoins.name();
            coinPrice.put(coinName, updateCoinPrice(coinName).getCurrentPrice());
        }
        return coinPrice;
    }

    public void addNewCoin(String coinName){
        System.out.println("New coin supported by Api: " + coinName);
        Coin coin = updateCoinPrice(coinName);
        User currentUser = getCurrentUser();
        currentUser.addCoin(coin);
        userRepository.save(currentUser);
    }

    public Map<String, BigDecimal> getUserCoinsPrice(){
        return getUserCoinRecords().stream().collect(Collectors.toMap(
                Coin::getName, coin -> updateCoinPrice(coin.getName()).getCurrentPrice()));
    }

    private Set<Coin> getUserCoinRecords() {
        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User user = getCurrentUser();
            return user.getCoins();
        }
        return Set.of();
    }

    public void detachCoinFromUser(String coinName){
        User currentUser = getCurrentUser();
        currentUser.getCoins().removeIf(coin -> coin.getName().equals(coinName));
        userRepository.save(currentUser);
    }

    private BigDecimal getCoinPriceFromApi(String coinName){
        Optional<HashMap<String, String>> coinJSON = coinApiService.getCoinJSON(coinName);
        HashMap<String, String> coinData = coinJSON.orElseThrow(() -> new RuntimeException("empty coin Json"));
        return new BigDecimal(coinData.get("rate"));
    }


    /**
     * This function updates a coin price loading it from external CoinApi based on reducing requests to CoinApi policy.
     * reducing api requests policy:
     * If price was updated less than 10 minutes ago nothing happens, otherwise get current price from api
     * @param coinName - currency cod e.g. BTC (Bitcoin)
     * @return currency price.
     */
    private Coin updateCoinPrice(String coinName){
        BigDecimal currentPrice;
        int minutes = 10;
        Coin coin = coinRepository.findCoinByName(coinName)
                .orElseGet(() -> coinRepository.save(new Coin(coinName, getCoinPriceFromApi(coinName))));
        if(!coin.getDateTime().isAfter(LocalDateTime.now().minusMinutes(minutes))){
            currentPrice = getCoinPriceFromApi(coinName);
            coin.setCurrentPrice(currentPrice);
        }
        return coinRepository.save(coin);
    }

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return  userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("cannot get user from db"));
    }
}