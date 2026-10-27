package com.nikanenka.java.testing.auto.ui.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.nikanenka.java.testing.auto.BaseTest;
import com.nikanenka.java.testing.auto.utils.ConsoleLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeSuite;

@Slf4j
public abstract class BaseUiTest extends BaseTest {

    @BeforeSuite
    public static void setupLogging() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .includeSelenideSteps(true));

        SelenideLogger.addListener("ConsoleSelenide", new ConsoleLogger());
    }
}
