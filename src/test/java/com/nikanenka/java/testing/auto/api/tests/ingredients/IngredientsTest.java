package com.nikanenka.java.testing.auto.api.tests.ingredients;

import com.nikanenka.java.testing.auto.api.clients.IngredientsClient;
import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.api.tests.BaseApiTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class IngredientsTest extends BaseApiTest {

    @LazyAutowired
    private IngredientsClient ingredientsClient;

    @Test
    void getIngredientsTest() {
        ingredientsClient.getIngredients()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/ingredients-schema.json"));
    }
}
