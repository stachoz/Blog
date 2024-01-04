package com.example.blogjava.custom_validators;

import com.example.blogjava.crypto.CoinApiService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class SupportedByCoinApiValidator implements ConstraintValidator<SupportedByCoinApi, String> {
    private final CoinApiService apiService;

    public SupportedByCoinApiValidator(CoinApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void initialize(SupportedByCoinApi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return apiService.isSupportedByApi(s);
    }
}
