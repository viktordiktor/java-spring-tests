package com.nikanenka.java.testing.auto.ui.tests.login;

import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.ui.pages.LoginPage;
import com.nikanenka.java.testing.auto.ui.pages.MenuPage;
import com.nikanenka.java.testing.auto.ui.tests.BaseUiTest;
import com.nikanenka.java.testing.auto.utils.CredentialProperties;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static com.codeborne.selenide.logevents.SelenideLogger.step;

@Slf4j
public class LoginTest extends BaseUiTest {

    private @LazyAutowired LoginPage loginPage;
    private @LazyAutowired CredentialProperties credentialProperties;

    @Test
    void loginTest() {
        step("Login with correct credentials", () -> {
            open(LoginPage.LOGIN_URL);
            loginPage.login(credentialProperties.getUsername(), credentialProperties.getPassword());
        });
        step("Check menu redirect", () ->
                webdriver().shouldHave(url(MenuPage.MENU_URL)));
    }
}
