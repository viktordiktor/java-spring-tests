package com.nikanenka.java.testing.auto.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Response of {@code GET /api/v1/orders} — a standard Spring page.
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersResponse {

    @JsonProperty("content")
    private List<OrderResponse> content;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("size")
    private int size;

    @JsonProperty("number")
    private int number;

    @JsonProperty("numberOfElements")
    private int numberOfElements;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("empty")
    private boolean empty;
}
