package com.nikanenka.java.testing.auto.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.nikanenka.java.testing.auto.annotations.PageObject;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;

@PageObject
@Getter
public class LoginPage {

    public static final String LOGIN_PATH = "/login";

    private final SelenideElement loginForm = $x("//form[@data-testid='login-form']");
    private final SelenideElement usernameInput = $x("//input[@data-testid='login-username']");
    private final SelenideElement passwordInput = $x("//input[@data-testid='login-password']");
    private final SelenideElement loginSubmitButton = $x("//button[@data-testid='login-submit']");

    public LoginPage login(String login, String password) {
        usernameInput.setValue(login);
        passwordInput.setValue(password);
        loginSubmitButton.click();
        return this;
    }
}
