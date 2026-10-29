package com.nikanenka.java.testing.auto.api.tests.ingredients;

import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.api.clients.IngredientsClient;
import com.nikanenka.java.testing.auto.api.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("API")
@Feature("Ingredients")
public class IngredientsTest extends BaseApiTest {

    @LazyAutowired
    private IngredientsClient ingredientsClient;

    @Test(groups = "api", description = "Ingredients list is available with a token and matches the schema")
    @Story("Ingredients list")
    void getIngredientsTest() {
        ingredientsClient.getIngredients()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/ingredients-schema.json"));
    }

    @Test(groups = "api", description = "Ingredients list is not available without a token")
    @Story("Authorization")
    void getIngredientsWithoutTokenReturns401() {
        ingredientsClient.getIngredientsWithoutToken()
                .then()
                .statusCode(401);
    }
}
