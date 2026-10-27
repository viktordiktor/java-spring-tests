package com.nikanenka.java.testing.auto.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.nikanenka.java.testing.auto.annotations.PageObject;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;

@PageObject
@Getter
public class LoginPage {
    public static final String LOGIN_URL = "https://shawarma.threadqa.ru/login";

    private final SelenideElement usernameInput = $x("//input[@data-testid='login-username']");
    private final SelenideElement passwordInput = $x("//input[@data-testid='login-password']");
    private final SelenideElement loginSubmitButton = $x("//button[@data-testid='login-submit']");

    public void login(String login, String password) {
        usernameInput.setValue(login);
        passwordInput.setValue(password);
        loginSubmitButton.click();
    }
}
