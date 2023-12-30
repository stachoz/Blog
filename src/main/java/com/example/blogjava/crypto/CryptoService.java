package com.example.blogjava.crypto;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CryptoService {
    private final CoinApiService coinApiService;

    public CryptoService(CoinApiService coinApiService) {
        this.coinApiService = coinApiService;
    }

    public String getBtcPrize(){
        Optional<HashMap<String, String>> coinJSON = coinApiService.getCoinJSON(BaseCoins.BTC.name());
        HashMap<String, String> coinData = coinJSON.orElseThrow(() -> new RuntimeException("empty coin Json"));
        return coinData.get("rate");
    }
}
