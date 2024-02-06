package org.botscrew.testtask.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case null -> true;
            default -> Arrays
                    .stream(annotation
                            .enumClass()
                            .getEnumConstants()
                    ).anyMatch(e -> e.toString().equals(value));
        };
    }
}
