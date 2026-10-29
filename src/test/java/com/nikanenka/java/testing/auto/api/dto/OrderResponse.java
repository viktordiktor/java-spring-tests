package com.nikanenka.java.testing.auto.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("recipe")
    private RecipeSlim recipe;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("placedAt")
    private String placedAt;

    @JsonProperty("etaAt")
    private String etaAt;

    @JsonProperty("payment")
    private PaymentSlim payment;
}
