package dev.memocode.memocode_platform.domain.common.exception;

import lombok.Getter;

@Getter
public enum GlobalError {
    VALIDATION_ERROR(1000000L, "유효성 검사 실패."),
    INTERNAL_SERVER_ERROR(1000001L, "알 수 없는 에러 발생. 관리자에게 문의하세요."),
    FORBIDDEN(1000002L, "해당 요청에 접근할 수 있는 권한이 없습니다."),
    UNAUTHORIZED(1000003L, "인증에 실패하였습니다."),
    ;

    private final Long code;
    private final String message;

    GlobalError(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
