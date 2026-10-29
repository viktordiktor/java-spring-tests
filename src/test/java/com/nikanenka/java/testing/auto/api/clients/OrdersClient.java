package com.nikanenka.java.testing.auto.api.clients;

import com.nikanenka.java.testing.auto.annotations.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

@ApiClient
public class OrdersClient extends BaseClient {

    private static final String ORDERS_URL = "/orders";

    public Response getOrders() {
        return given()
                .spec(tokenSpecManager.getAuthenticatedSpec())
                .when()
                .get(ORDERS_URL);
    }

    public Response getOrders(int page, int size) {
        return given()
                .spec(tokenSpecManager.getAuthenticatedSpec())
                .queryParam("page", page)
                .queryParam("size", size)
                .when()
                .get(ORDERS_URL);
    }

    public Response getOrdersByStatus(String status) {
        return given()
                .spec(tokenSpecManager.getAuthenticatedSpec())
                .queryParam("status", status)
                .when()
                .get(ORDERS_URL);
    }

    /**
     * Request without authorization to verify the endpoint is protected.
     *
     * @return response with 401 status
     */
    public Response getOrdersWithoutToken() {
        return given()
                .baseUri(apiProperties.getBaseUri())
                .when()
                .get(ORDERS_URL);
    }
}
