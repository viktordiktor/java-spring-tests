package com.nikanenka.java.testing.auto.api.tests;

import com.nikanenka.java.testing.auto.BaseTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

public abstract class BaseApiTest extends BaseTest {

    @BeforeSuite
    public static void globalSetup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
