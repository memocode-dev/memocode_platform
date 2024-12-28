package dev.memocode.memocode_platform.domain.common.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class LocaleConfig {

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        log.info("Date in Asia/Seoul: {}", new Date());
    }
}
