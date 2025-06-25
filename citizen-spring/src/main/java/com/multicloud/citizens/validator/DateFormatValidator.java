package com.multicloud.citizens.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, LocalDate> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true; // Leave null check to @NotNull

        try {
            String formatted = value.format(dtf);
            LocalDate parsed = LocalDate.parse(formatted, dtf);

            return value.equals(parsed); // round-trip check
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Date must match format 'dd-MM-yyyy': got '" + value + "'"
            ).addConstraintViolation();
            return false;
        }
    }




}
