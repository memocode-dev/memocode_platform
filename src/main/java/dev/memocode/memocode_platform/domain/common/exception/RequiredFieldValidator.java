package dev.memocode.memocode_platform.domain.common.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static dev.memocode.memocode_platform.domain.common.exception.GlobalError.INTERNAL_SERVER_ERROR;

public class RequiredFieldValidator implements ConstraintValidator<RequiredField, Object> {
    private String message;

    @Override
    public void initialize(RequiredField requiredField) {
        this.message = requiredField.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new InternalException(INTERNAL_SERVER_ERROR, message);
        }
        return true;
    }
}
