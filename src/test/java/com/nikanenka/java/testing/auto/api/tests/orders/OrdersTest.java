package com.nikanenka.java.testing.auto.api.tests.orders;

import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.api.clients.OrdersClient;
import com.nikanenka.java.testing.auto.api.dto.OrdersResponse;
import com.nikanenka.java.testing.auto.api.dto.OrderResponse;
import com.nikanenka.java.testing.auto.api.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("API")
@Feature("Orders")
public class OrdersTest extends BaseApiTest {

    private static final String PENDING = "PENDING";
    private static final List<String> ALL_STATUSES =
            List.of("PENDING", "PAID", "PREPARING", "DONE", "CANCELLED");

    @LazyAutowired
    private OrdersClient ordersClient;

    @Test(groups = "api", description = "GET /orders returns an orders page matching the contract")
    @Story("Orders page contract")
    void getOrdersReturnsPage() {
        OrdersResponse page = ordersClient.getOrders()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/orders-page-schema.json"))
                .extract()
                .as(OrdersResponse.class);

        assertThat(page.getContent())
                .as("numberOfElements matches content size")
                .hasSize(page.getNumberOfElements());

        assertThat(page.getTotalElements())
                .as("totalElements is not less than page size")
                .isGreaterThanOrEqualTo(page.getContent().size());

        assertThat(page.getNumber()).as("first page has number 0").isZero();
        assertThat(page.isFirst()).as("first page flag is set").isTrue();
        assertThat(page.isEmpty())
                .as("empty flag matches content")
                .isEqualTo(page.getContent().isEmpty());

        assertThat(page.getContent())
                .as("every order is well-formed")
                .allSatisfy(this::assertOrderIsWellFormed);
    }

    @Test(groups = "api", description = "The size parameter limits the page size")
    @Story("Pagination")
    void getOrdersRespectsPageSize() {
        int requestedSize = 5;

        OrdersResponse page = ordersClient.getOrders(0, requestedSize)
                .then()
                .statusCode(200)
                .extract()
                .as(OrdersResponse.class);

        assertThat(page.getSize()).as("server confirms the requested size").isEqualTo(requestedSize);
        assertThat(page.getContent()).as("page holds no more orders than requested").hasSizeLessThanOrEqualTo(requestedSize);
    }

    @Test(groups = "api", description = "The status filter returns orders with the requested status only")
    @Story("Status filter")
    void getOrdersFiltersByStatus() {
        OrdersResponse page = ordersClient.getOrdersByStatus(PENDING)
                .then()
                .statusCode(200)
                .extract()
                .as(OrdersResponse.class);

        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent())
                .as("selection contains PENDING orders only")
                .allSatisfy(order -> assertThat(order.getStatus()).isEqualTo(PENDING));
    }

    @Test(groups = "api", description = "Invalid status value is rejected with 400")
    @Story("Status filter")
    void getOrdersWithInvalidStatusReturns400() {
        ordersClient.getOrdersByStatus("NOT_A_REAL_STATUS")
                .then()
                .statusCode(400);
    }

    @Test(groups = "api", description = "Orders list is not available without a token")
    @Story("Authorization")
    void getOrdersWithoutTokenReturns401() {
        ordersClient.getOrdersWithoutToken()
                .then()
                .statusCode(401);
    }

    private void assertOrderIsWellFormed(OrderResponse order) {
        assertThat(order.getId()).as("id").isPositive();
        assertThat(order.getStatus()).as("status").isIn(ALL_STATUSES);
        assertThat(order.getQty()).as("qty").isPositive();
        assertThat(order.getTotalPrice()).as("totalPrice").isPositive();
        assertThat(order.getPlacedAt()).as("placedAt").isNotBlank();
        assertThat(order.getRecipe()).as("recipe").isNotNull();
        assertThat(order.getRecipe().getId()).as("recipe.id").isPositive();
        assertThat(order.getRecipe().getName()).as("recipe.name").isNotBlank();
        assertThat(order.getRecipe().getPrice()).as("recipe.price").isPositive();
    }
}
