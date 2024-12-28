package dev.memocode.memocode_platform.api.handler;

import dev.memocode.memocode_platform.api.response.ErrorResponse;
import dev.memocode.memocode_platform.domain.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

import static dev.memocode.memocode_platform.domain.common.exception.GlobalError.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex, HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getGlobalError().name())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(createGlobalExceptionStatus(ex))
                .body(errorResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(FORBIDDEN.name())
                .message(FORBIDDEN.getMessage())
                .build();

        return ResponseEntity
                .status(403)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(VALIDATION_ERROR.name())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(400)
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(VALIDATION_ERROR.name())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(400)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        log.error("Exception caught: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(INTERNAL_SERVER_ERROR.name())
                .message(INTERNAL_SERVER_ERROR.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    public int createGlobalExceptionStatus(GlobalException ex) {
        return switch (ex) {
            case BadRequestException badRequestException -> 400;
            case UnauthorizedException unauthorizedException -> 401;
            case ForbiddenException forbiddenException -> 403;
            case NotFoundException notFoundException -> 404;
            case ConflictException conflictException -> 409;
            case UnsupportedMediaTypeException unsupportedMediaTypeException -> 415;
            case UnprocessableEntityException unprocessableEntityException -> 422;
            case TooManyRequestsException tooManyRequestsException -> 429;
            case InternalException internalException-> 500;
            case ServiceUnavailableException serviceUnavailableException -> 503;
            default -> 500;
        };
    }

    private String extractStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
