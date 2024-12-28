package dev.memocode.memocode_platform.domain.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final GlobalError globalError;

    public GlobalException(GlobalError globalError) {
        super(globalError.getMessage());
        this.globalError = globalError;
    }

    public GlobalException(GlobalError globalError, String message) {
        super(message);
        this.globalError = globalError;
    }
}
