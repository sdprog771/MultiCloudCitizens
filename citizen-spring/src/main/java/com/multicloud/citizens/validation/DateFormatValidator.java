package com.multicloud.citizens.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, Date> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) return true; // handle @NotNull separately
        sdf.setLenient(false);
        try {
            String formatted = sdf.format(value);
            Date parsed = sdf.parse(formatted);
            return value.equals(parsed);
        } catch (ParseException e) {
            return false;
        }
    }




}
