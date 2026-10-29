package com.nikanenka.java.testing.auto.ui.tests.login;

import com.codeborne.selenide.Configuration;
import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.ui.pages.LoginPage;
import com.nikanenka.java.testing.auto.ui.pages.MenuPage;
import com.nikanenka.java.testing.auto.ui.tests.BaseUiTest;
import com.nikanenka.java.testing.auto.utils.CredentialProperties;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlStartingWith;
import static com.codeborne.selenide.logevents.SelenideLogger.step;

@Slf4j
@Epic("UI")
@Feature("Authentication")
public class LoginTest extends BaseUiTest {

    private static final String WRONG_PASSWORD = "definitely-wrong-password";

    private @LazyAutowired LoginPage loginPage;
    private @LazyAutowired MenuPage menuPage;
    private @LazyAutowired CredentialProperties credentialProperties;

    @Test(groups = "ui", description = "Valid credentials redirect the user to the menu page")
    @Story("Login")
    void loginWithValidCredentials() {
        step("Open login page", () ->
                open(LoginPage.LOGIN_PATH));

        step("Login with correct credentials", () -> {
            loginPage.getLoginForm().shouldBe(visible);
            loginPage.login(credentialProperties.getUsername(), credentialProperties.getPassword());
        });

        step("Check redirect to menu page", () ->
                webdriver().shouldHave(urlStartingWith(Configuration.baseUrl + MenuPage.MENU_PATH)));

        step("Check menu page is loaded", () -> {
            menuPage.getNavbar().shouldBe(visible);
            menuPage.getRecipeCards().shouldHave(sizeGreaterThan(0));
        });
    }

    @Test(groups = "ui", description = "Wrong password keeps the user on the login page")
    @Story("Login")
    void loginWithWrongPassword() {
        step("Open login page", () ->
                open(LoginPage.LOGIN_PATH));

        step("Login with wrong password", () -> {
            loginPage.getLoginForm().shouldBe(visible);
            loginPage.login(credentialProperties.getUsername(), WRONG_PASSWORD);
        });

        step("Check user stays on login page", () -> {
            webdriver().shouldHave(urlStartingWith(Configuration.baseUrl + LoginPage.LOGIN_PATH));
            menuPage.getNavbar().shouldNotBe(visible);
        });
    }
}
