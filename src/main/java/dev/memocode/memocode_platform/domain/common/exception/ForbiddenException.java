package dev.memocode.memocode_platform.domain.common.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends GlobalException {
    private final GlobalError globalError;
    private final String message;

    public ForbiddenException(GlobalError globalError) {
        super(globalError);
        this.globalError = globalError;
        this.message = globalError.getMessage();
    }

    public ForbiddenException(GlobalError globalError, String message) {
        super(globalError, message);
        this.globalError = globalError;
        this.message = message;
    }
}
