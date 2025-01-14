package dev.memocode.memocode_platform.domain.common.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ParameterObject
public class PageRq {
    @Parameter(description = "페이지")
    private int page = 0;
    @Parameter(description = "페이지 크기")
    private int pageSize = 10;

    public Pageable toPageable() {
        return PageRequest.of(this.getPage(), this.getPageSize());
    }
}
