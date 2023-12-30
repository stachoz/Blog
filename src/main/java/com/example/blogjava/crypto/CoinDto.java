package com.example.blogjava.crypto;

public class CoinDto {
    private String name;

    public CoinDto(String name) {
        this.name = name;
    }

    public CoinDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
