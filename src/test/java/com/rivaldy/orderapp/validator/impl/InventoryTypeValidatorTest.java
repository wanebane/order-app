package com.rivaldy.orderapp.validator.impl;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryTypeValidatorTest {

    private InventoryTypeValidator validator;
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeEach
    void setUp() {
        validator = new InventoryTypeValidator();
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(builder);
        when(builder.addConstraintViolation())
                .thenReturn(context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"T", "W"})
    void isValid_WhenValidType_ShouldReturnTrue(String type) {
        assertTrue(validator.isValid(type, context));
        verifyNoInteractions(context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "S", "X", "TW", "1", "@"})
    void isValid_WhenInvalidType_ShouldReturnFalse(String invalidType) {
        assertFalse(validator.isValid(invalidType, context));
    }

    @Test
    void isValid_WhenNull_ShouldReturnFalse() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void isValid_WhenInvalid_ShouldUseCorrectMessage() {
        validator.isValid("INVALID", context);

        verify(context).buildConstraintViolationWithTemplate(
                "Type must be either 'T' or 'W'"
        );
        verify(builder).addConstraintViolation();
    }
}