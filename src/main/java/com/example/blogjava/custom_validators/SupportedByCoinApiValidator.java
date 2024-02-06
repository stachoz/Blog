package com.example.blogjava.custom_validators;

import com.example.blogjava.crypto.CoinApiService;
import com.example.blogjava.crypto.CoinRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class SupportedByCoinApiValidator implements ConstraintValidator<SupportedByCoinApi, String> {
    private final CoinApiService apiService;
    private final CoinRepository coinRepository;

    public SupportedByCoinApiValidator(CoinApiService apiService, CoinRepository coinRepository) {
        this.apiService = apiService;
        this.coinRepository = coinRepository;
    }

    @Override
    public void initialize(SupportedByCoinApi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String coinName, ConstraintValidatorContext constraintValidatorContext) {
        return coinRepository.existsByName(coinName) || apiService.getCoinJSON(coinName)
                .filter(json -> json.containsKey("error"))
                .isEmpty();
    }
}
