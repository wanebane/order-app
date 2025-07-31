package com.rivaldy.orderapp.validator.annotation;

import com.rivaldy.orderapp.validator.impl.InventoryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InventoryTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInventoryType {
    String message() default "Type must be either 'T' or 'W'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
