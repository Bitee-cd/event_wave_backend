package com.bitee.event.utils;

import com.bitee.event.utils.EnumValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValid, String> {
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = annotation;
    }


    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (valueForValidation.equals(enumValue.toString())
                ) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}