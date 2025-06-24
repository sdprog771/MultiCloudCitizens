package com.multicloud.citizens.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NineDigitLongValidator implements ConstraintValidator<NineDigitLong, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true; // use @NotNull if needed
        return value >= 100_000_000L && value <= 999_999_999L;
    }
}
