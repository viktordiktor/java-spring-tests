package com.nikanenka.java.testing.auto.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSlim {

    @JsonProperty("method")
    private String method;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("txnId")
    private String txnId;

    @JsonProperty("status")
    private String status;
}
