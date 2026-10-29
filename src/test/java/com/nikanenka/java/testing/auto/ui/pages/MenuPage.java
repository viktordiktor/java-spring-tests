package com.nikanenka.java.testing.auto.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.nikanenka.java.testing.auto.annotations.PageObject;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;

@PageObject
@Getter
public class MenuPage {

    public static final String MENU_PATH = "/menu";

    private final SelenideElement navbar = $x("//div[@data-testid='navbar-desktop']");
    private final SelenideElement menuLink = $x("//a[@data-testid='nav-menu']");
    private final SelenideElement searchInput = $x("//input[@data-testid='menu-search']");
    private final SelenideElement logoutButton = $x("//button[@data-testid='logout-btn']");
    private final ElementsCollection recipeCards = $$x("//div[@data-testid='recipe-card']");
    private final ElementsCollection recipeNames = $$x("//h3[@data-testid='recipe-name']");

    private final SelenideElement emptySearchResult = $x("//p[contains(text(), 'NO MATCHES')]");

    public MenuPage search(String query) {
        searchInput.setValue(query);
        return this;
    }
}
