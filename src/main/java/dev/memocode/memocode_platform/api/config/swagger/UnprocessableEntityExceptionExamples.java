package dev.memocode.memocode_platform.api.config.swagger;

import dev.memocode.memocode_platform.domain.common.exception.GlobalError;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnprocessableEntityExceptionExamples {
    GlobalError[] values();
}
