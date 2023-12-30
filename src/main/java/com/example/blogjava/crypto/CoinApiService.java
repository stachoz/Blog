package com.example.blogjava.crypto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CoinApiService {
    private static final String API_URL = "https://rest.coinapi.io/v1/exchangerate/";
    private static final String API_KEY = System.getenv("COIN_API_KEY");


    public Optional<HashMap<String, String>> getCoinJSON(String coinSymbol){
        String currency = "USD";
        String url = createUrl(coinSymbol, currency);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse<String> response = getResponse(url);
            String jsonString = response.body();
            JSONParser jsonParser = new JSONParser(jsonString);
            HashMap<String, String> result = objectMapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
            });
            return Optional.of(result);
        }
        catch (ConnectException e){
            System.out.println("No internet connection");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean isSupportedByApi(String coinSymbol){
        String currency = "PLN";
        String url = createUrl(coinSymbol, currency);
        try {
            HttpResponse<String> response = getResponse(url);
            int statusCode = response.statusCode();
            if(statusCode >= 200 && statusCode < 300){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private HttpResponse<String> getResponse(String url) throws URISyntaxException, IOException, InterruptedException {
        final String HEADER = "X-CoinAPI-Key";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(HEADER, API_KEY)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String createUrl(String coinSymbol, String currency){
        return API_URL + coinSymbol.toUpperCase() + "/" + currency;
    }
}
