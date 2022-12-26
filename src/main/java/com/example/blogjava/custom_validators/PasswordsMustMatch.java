package com.example.blogjava.custom_validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatch.class)
public @interface PasswordsMustMatch {
    String message() default "Passwords are not equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
