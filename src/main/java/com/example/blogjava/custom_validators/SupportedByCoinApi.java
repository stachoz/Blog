package com.example.blogjava.custom_validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupportedByCoinApiValidator.class)
public @interface SupportedByCoinApi {
    String message() default "Coin is not supported by CoinAPI";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
