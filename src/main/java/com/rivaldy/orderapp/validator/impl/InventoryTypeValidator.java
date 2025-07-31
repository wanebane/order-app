package com.rivaldy.orderapp.validator.impl;

import com.rivaldy.orderapp.validator.annotation.ValidInventoryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class InventoryTypeValidator implements ConstraintValidator<ValidInventoryType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || (!value.equals("T") && !value.equals("W"))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Type must be either 'T' or 'W'"
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
