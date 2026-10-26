package com.nikanenka.java.testing.auto.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("type")
    private String type;

    @JsonProperty("username")
    private String username;

    @JsonProperty("expiresIn")
    private Long expiresIn;
}
