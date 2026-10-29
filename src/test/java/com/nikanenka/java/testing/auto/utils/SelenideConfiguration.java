package com.nikanenka.java.testing.auto.utils;

import com.codeborne.selenide.Configuration;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Applies {@code selenide.*} properties to the static Selenide configuration.
 * <p>
 * Selenide reads {@link Configuration} when a driver is created, so the values are
 * applied in {@link PostConstruct} before any test runs.
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "selenide")
public class SelenideConfiguration {

    private String baseUrl;
    private String browser = "chrome";
    private boolean headless;
    private long timeout = 10_000;
    private long pageLoadTimeout = 30_000;
    private String browserSize = "1920x1080";

    @PostConstruct
    public void apply() {
        if (baseUrl != null) {
            Configuration.baseUrl = baseUrl;
        }
        Configuration.browser = browser;
        Configuration.headless = headless;
        Configuration.timeout = timeout;
        Configuration.pageLoadTimeout = pageLoadTimeout;
        Configuration.browserSize = browserSize;

        log.info("Selenide configured: browser={}, headless={}, baseUrl={}, timeout={}ms",
                browser, headless, baseUrl, timeout);
    }
}
