package dev.memocode.memocode_platform.domain.common.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredFieldValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredField {
    String message() default "required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
