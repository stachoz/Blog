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
        Coin coin = updateCoinPrice(coinName);
        User currentUser = getCurrentUser();
        currentUser.addCoin(coin);
        userRepository.save(currentUser);
    }

    public Map<String, BigDecimal> getUserCoinsPrice(){
        return getUserCoinRecords().stream().collect(Collectors.toMap(
                Coin::getName, coin -> updateCoinPrice(coin.getName()).getCurrentPrice()));
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

    private BigDecimal getCoinPriceFromApi(String coinName){
        Optional<HashMap<String, String>> coinJSON = coinApiService.getCoinJSON(coinName);
        Map<String, String> coinData = coinJSON.orElseThrow(() -> new RuntimeException("empty coin Json"));
        if(coinData.containsKey("error")) throw new ApiException("");
        return new BigDecimal(coinData.get("rate"));
    }


    /**
     * This function updates a coin price loading it from external CoinApi based on reducing requests to CoinApi policy.
     * reducing api requests policy:
     * If price was updated less than 10 minutes ago nothing happens, otherwise get current price from api
     * @param coinName - currency cod e.g. BTC (Bitcoin)
     * @return Coin with updated price.
     */
    private Coin updateCoinPrice(String coinName){
        int minutesThreshold = 10;
        return coinRepository.findCoinByName(coinName)
                .map(coin -> {
                    if(!coin.getDateTime().isAfter(LocalDateTime.now().minusMinutes(minutesThreshold))){
                        savingCoinProcess(coin);
                    }
                    return coin;
                })
                .orElseGet(() -> savingCoinProcess(new Coin()));
    }

    private Coin savingCoinProcess(Coin coin){
        try {
            BigDecimal currentPrice = getCoinPriceFromApi(coin.getName());
            coin.setCurrentPrice(currentPrice);
            return coinRepository.save(coin);
        }
        catch (ApiException e){
            System.out.println("unable to update coin price");
        }
        return coin;
    }

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return  userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("cannot get user from db"));
    }
}