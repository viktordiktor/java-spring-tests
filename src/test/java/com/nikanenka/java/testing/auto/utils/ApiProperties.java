package com.nikanenka.java.testing.auto.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    private String baseUri;
    private String loginPath;
}
