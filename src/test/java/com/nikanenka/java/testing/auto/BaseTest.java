package com.nikanenka.java.testing.auto;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SpringBootTest
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    /** Writes environment.properties so the Allure report shows the stand and runtime details. */
    @BeforeSuite(alwaysRun = true)
    public void writeAllureEnvironment() throws IOException {
        Path resultsDirectory = Path.of(System.getProperty("allure.results.directory", "target/allure-results"));
        Files.createDirectories(resultsDirectory);

        Properties environment = new Properties();
        environment.setProperty("Stand", resolve("selenide.baseUrl", "SELENIDE_BASE_URL", "https://shawarma.threadqa.ru"));
        environment.setProperty("API base URI", resolve("api.baseUri", "API_BASE_URI", "https://shawarma.threadqa.ru/api/v1"));
        environment.setProperty("Browser", resolve("selenide.browser", "SELENIDE_BROWSER", "chrome"));
        environment.setProperty("Headless", resolve("selenide.headless", "SELENIDE_HEADLESS", "false"));
        environment.setProperty("Java", System.getProperty("java.version"));
        environment.setProperty("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));

        try (OutputStream output = Files.newOutputStream(resultsDirectory.resolve("environment.properties"))) {
            environment.store(output, null);
        }
    }

    private static String resolve(String systemProperty, String environmentVariable, String fallback) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }
        return value == null || value.isBlank() ? fallback : value;
    }
}
