package com.example.blogjava.crypto;

import jakarta.validation.constraints.NotBlank;

public class CoinDto {
    @NotBlank(message = "can't be empty")
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
