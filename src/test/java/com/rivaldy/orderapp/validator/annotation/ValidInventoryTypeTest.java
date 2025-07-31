package com.rivaldy.orderapp.validator.annotation;

import jakarta.validation.Constraint;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

class ValidInventoryTypeTest {

    @Test
    void validInventoryTypeAnnotation_ShouldHaveCorrectConfiguration() {
        Class<ValidInventoryType> annotationClass = ValidInventoryType.class;

        assertTrue(annotationClass.isAnnotationPresent(Documented.class));
        assertTrue(annotationClass.isAnnotationPresent(Constraint.class));

        Target target = annotationClass.getAnnotation(Target.class);
        assertArrayEquals(new ElementType[]{ElementType.FIELD}, target.value());

        Retention retention = annotationClass.getAnnotation(Retention.class);
        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }
}
