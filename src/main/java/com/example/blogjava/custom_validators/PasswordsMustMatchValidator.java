package com.example.blogjava.custom_validators;

import com.example.blogjava.user.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMustMatchValidator implements ConstraintValidator<PasswordsMustMatch, UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto dto, ConstraintValidatorContext constraintValidatorContext) {
        String pass = dto.getPassword();
        return pass.equals(dto.getPasswordRepeat());
    }
}
