package dev.memocode.memocode_platform.api.config.swagger;

import dev.memocode.memocode_platform.domain.common.exception.GlobalError;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UNOCARE PLATFORM API")
                        .description(generateErrorDescription())
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .bearerFormat("JWT")));
    }

    private String generateErrorDescription() {
        StringBuilder descriptionBuilder = new StringBuilder("MEMOCODE PLATFORM\n\n");

        descriptionBuilder.append("\n에러 코드 목록:\n");

        for (GlobalError error : GlobalError.values()) {
            descriptionBuilder.append(String.format("- %s: %s\n", error.name(), error.getMessage()));
        }

        return descriptionBuilder.toString();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ForbiddenExceptionExamples forbiddenExceptionExamples = handlerMethod.getMethodAnnotation(ForbiddenExceptionExamples.class);
            BadRequestExceptionExamples badRequestExceptionExamples = handlerMethod.getMethodAnnotation(BadRequestExceptionExamples.class);
            UnprocessableEntityExceptionExamples unprocessableEntityExceptionExamples = handlerMethod.getMethodAnnotation(UnprocessableEntityExceptionExamples.class);
            NotFoundExceptionExamples notFoundExceptionExamples = handlerMethod.getMethodAnnotation(NotFoundExceptionExamples.class);
            ConflictExceptionExamples conflictExceptionExamples = handlerMethod.getMethodAnnotation(ConflictExceptionExamples.class);

            StringBuilder descriptionBuilder = new StringBuilder(operation.getDescription() != null ? operation.getDescription() : "");
            if (badRequestExceptionExamples != null || forbiddenExceptionExamples != null ||
                    unprocessableEntityExceptionExamples != null || notFoundExceptionExamples != null ||
                    conflictExceptionExamples != null
            ) {
                descriptionBuilder.append("\n에러 코드\n");

                if (badRequestExceptionExamples != null) {
                    descriptionBuilder.append("  - 400 BadRequest:\n");
                    Arrays.stream(badRequestExceptionExamples.values()).forEach(globalError ->
                            descriptionBuilder.append("    - %s : %s\n".formatted(
                                    globalError.name(), globalError.getMessage())));
                }

                if (forbiddenExceptionExamples != null) {
                    descriptionBuilder.append("  - 403 Forbidden:\n");
                    Arrays.stream(forbiddenExceptionExamples.values()).forEach(globalError ->
                            descriptionBuilder.append("    - %s : %s\n".formatted(
                                    globalError.name(), globalError.getMessage())));
                }

                if (notFoundExceptionExamples != null) {
                    descriptionBuilder.append("  - 404 NotFound:\n");
                    Arrays.stream(notFoundExceptionExamples.values()).forEach(globalError ->
                            descriptionBuilder.append("    - %s : %s\n".formatted(
                                    globalError.name(), globalError.getMessage())));
                }

                if (conflictExceptionExamples != null) {
                    descriptionBuilder.append("  - 409 Conflict:\n");
                    Arrays.stream(conflictExceptionExamples.values()).forEach(globalError ->
                            descriptionBuilder.append("    - %s : %s\n".formatted(
                                    globalError.name(), globalError.getMessage())));
                }

                if (unprocessableEntityExceptionExamples != null) {
                    descriptionBuilder.append("  - 422 Unprocessable Entity:\n");
                    Arrays.stream(unprocessableEntityExceptionExamples.values()).forEach(globalError ->
                            descriptionBuilder.append("    - %s : %s\n".formatted(
                                    globalError.name(), globalError.getMessage())));
                }
            }

            operation.setDescription(descriptionBuilder.toString());

            return operation;
        };
    }

    @Bean
    public OperationCustomizer customizeOperationId() {
        return (operation, handlerMethod) -> {
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            operation.setOperationId(className + "_" + methodName);
            return operation;
        };
    }
}
