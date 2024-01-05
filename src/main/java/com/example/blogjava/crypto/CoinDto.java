package com.example.blogjava.crypto;

import com.example.blogjava.custom_validators.SupportedByCoinApi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CoinDto {
    @NotBlank(message = "can't be empty")
    @Size(max = 10, message = "too long value (max 10)")
    @SupportedByCoinApi
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
