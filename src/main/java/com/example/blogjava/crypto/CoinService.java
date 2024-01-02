package com.example.blogjava.crypto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class CoinService {
    private final CoinApiService coinApiService;
    private final CoinRepository coinRepository;

    public CoinService(CoinApiService coinApiService, CoinRepository coinRepository) {
        this.coinApiService = coinApiService;
        this.coinRepository = coinRepository;
    }


    /**
     * @return HashMap<String, BigDecimal>, key - coin name, value - coin price
     * there is possible exception because of api requests limitations
     */
    public HashMap<String,BigDecimal> getBaseCoinPrices(){
        HashMap<String, BigDecimal> coinPrice = new LinkedHashMap<>();
        int minutes = 10;
        for (BaseCoins baseCoins : BaseCoins.values()) {
            try{
                String coinName = baseCoins.name();
                Coin coin = coinRepository.findCoinByName(coinName)
                                .orElseGet(() -> coinRepository.save(new Coin(coinName, getCoinPrice(coinName))));
                BigDecimal currentPrice;
                if(coin.getDateTime().isAfter(LocalDateTime.now().minusMinutes(minutes))) currentPrice = coin.getCurrentPrice();
                else{
                    currentPrice = getCoinPrice(coinName);
                    coin.setCurrentPrice(currentPrice);
                    coinRepository.save(coin);
                }
                coinPrice.put(coinName, currentPrice);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return coinPrice;
    }

    private BigDecimal getCoinPrice(String coinName){
        Optional<HashMap<String, String>> coinJSON = coinApiService.getCoinJSON(coinName);
        HashMap<String, String> coinData = coinJSON.orElseThrow(() -> new RuntimeException("empty coin Json"));
        return new BigDecimal(coinData.get("rate"));
    }
}