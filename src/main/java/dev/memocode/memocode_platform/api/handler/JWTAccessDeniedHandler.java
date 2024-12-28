package dev.memocode.memocode_platform.api.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.memocode.memocode_platform.api.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static dev.memocode.memocode_platform.domain.common.exception.GlobalError.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 상태 코드 설정
        response.setContentType("application/json");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(FORBIDDEN.name())
                .message(FORBIDDEN.getMessage())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
