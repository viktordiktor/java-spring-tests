package com.nikanenka.java.testing.auto.api.clients;

import com.nikanenka.java.testing.auto.annotations.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

@ApiClient
public class IngredientsClient extends BaseClient {

    private static final String INGREDIENTS_URL = "/ingredients";

    public Response getIngredients() {
        return given()
                .spec(tokenSpecManager.getAuthenticatedSpec())
                .when()
                .get(INGREDIENTS_URL);
    }

    public Response getIngredientsWithoutToken() {
        return given()
                .baseUri(apiProperties.getBaseUri())
                .when()
                .get(INGREDIENTS_URL);
    }
}
