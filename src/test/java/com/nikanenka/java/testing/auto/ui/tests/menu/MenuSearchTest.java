package com.nikanenka.java.testing.auto.ui.tests.menu;

import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.ui.pages.LoginPage;
import com.nikanenka.java.testing.auto.ui.pages.MenuPage;
import com.nikanenka.java.testing.auto.ui.tests.BaseUiTest;
import com.nikanenka.java.testing.auto.utils.CredentialProperties;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.step;


@Slf4j
@Epic("UI")
@Feature("Menu")
public class MenuSearchTest extends BaseUiTest {

    /** Search is server-side and case-insensitive: "комбо" matches "КОМБО БОСС", "КОМБО КОЛА", etc. */
    private static final String MATCHING_QUERY = "комбо";
    private static final String NON_MATCHING_QUERY = "zzzzzz";

    private @LazyAutowired LoginPage loginPage;
    private @LazyAutowired MenuPage menuPage;
    private @LazyAutowired CredentialProperties credentialProperties;

    @BeforeMethod(alwaysRun = true)
    void openMenuPage() {
        step("Open menu page as authenticated user", () -> {
            open(LoginPage.LOGIN_PATH);
            loginPage.getLoginForm().shouldBe(visible);
            loginPage.login(credentialProperties.getUsername(), credentialProperties.getPassword());
            menuPage.getSearchInput().shouldBe(visible);
            menuPage.getRecipeCards().shouldHave(sizeGreaterThan(0));
        });
    }

    @Test(groups = "ui", description = "Search returns only recipes whose name contains the query")
    @Story("Recipe search")
    void searchFiltersRecipesByName() {
        step("Search for '" + MATCHING_QUERY + "'", () ->
                menuPage.search(MATCHING_QUERY));

        step("Check only matching recipes are shown", () ->
                assertOnlyRecipesMatching(MATCHING_QUERY));
    }

    @Test(groups = "ui", description = "Search with no matches shows the empty state instead of cards")
    @Story("Recipe search")
    void searchWithUnknownQueryShowsNoMatches() {
        step("Search for '" + NON_MATCHING_QUERY + "'", () ->
                menuPage.search(NON_MATCHING_QUERY));

        step("Check empty result state", () -> {
            menuPage.getRecipeCards().shouldHave(size(0));
            menuPage.getEmptySearchResult().shouldBe(visible);
        });
    }

    private void assertOnlyRecipesMatching(String query) {
        menuPage.getRecipeCards().shouldHave(sizeGreaterThan(0));
        menuPage.getRecipeNames().shouldHave(allMatch(
                "name contains '" + query + "'",
                name -> name.getText().toLowerCase().contains(query.toLowerCase())));
    }
}
