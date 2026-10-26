package com.nikanenka.java.testing.auto.api.utils;

import com.nikanenka.java.testing.auto.api.dto.TokenRequest;
import com.nikanenka.java.testing.auto.api.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static io.restassured.RestAssured.given;

@Component
@RequiredArgsConstructor
@Log4j2
public class TokenSpecManager {

    private final ApiProperties apiProperties;

    private String cachedToken;
    private Instant tokenExpirationTime;

    public RequestSpecification getAuthenticatedSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(apiProperties.getBaseUri())
                .setContentType(ContentType.JSON)
                .setAuth(RestAssured.oauth2(getToken()))
                .build();
    }

    private synchronized String getToken() {
        if (cachedToken == null || isTokenExpired()) {
            fetchNewToken();
        }
        return cachedToken;
    }

    private boolean isTokenExpired() {
        if (tokenExpirationTime == null) {
            return true;
        }
        return Instant.now().isAfter(tokenExpirationTime.minusSeconds(30));
    }

    private void fetchNewToken() {
        TokenResponse response = given()
                .baseUri(apiProperties.getBaseUri())
                .contentType(ContentType.JSON)
                .body(TokenRequest.builder()
                        .username(apiProperties.getUsername())
                        .password(apiProperties.getPassword())
                        .build())
                .when()
                .post(apiProperties.getLoginPath())
                .then()
                .statusCode(200)
                .extract()
                .as(TokenResponse.class);

        this.cachedToken = response.getToken();
        this.tokenExpirationTime = Instant.now().plusMillis(response.getExpiresIn());
        log.info("Token successfully fetched");
    }
}
