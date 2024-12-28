package dev.memocode.memocode_platform.domain.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends GlobalException {
    private final GlobalError globalError;
    private final String message;

    public NotFoundException(GlobalError globalError) {
        super(globalError, globalError.getMessage());
        this.globalError = globalError;
        this.message = globalError.getMessage();
    }

    public NotFoundException(GlobalError globalError, String message) {
        super(globalError, message);
        this.globalError = globalError;
        this.message = message;
    }
}
