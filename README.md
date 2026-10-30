# JavaTestingAutomation

Test automation framework for the [shawarma.threadqa.ru](https://shawarma.threadqa.ru) demo stand:
UI tests on Selenide and API tests on RestAssured, sharing a single Spring context.

## Stack

| Component | Version | Role |
|---|---|---|
| Java | 17+ | language |
| Spring Boot | 4.1.2 | DI, configuration |
| TestNG | 7.12.0 | test engine |
| Selenide | 7.19.0 | UI automation |
| RestAssured | 6.3.0 | API tests |
| Allure | 3.0.1 | reporting |
| Lombok | — | less boilerplate |

## Structure

```
src/main/java/.../JavaTestingAutomationApplication.java  — Spring Boot entry point
src/test/java/.../
├── BaseTest.java                    — @SpringBootTest + TestNG
├── annotations/                     — @LazyAutowired, @PageObject, @ApiClient
├── api/
│   ├── clients/                     — BaseClient, IngredientsClient, OrdersClient
│   ├── dto/                         — TokenRequest, TokenResponse, OrdersResponse, OrderResponse, ...
│   └── tests/                       — BaseApiTest, IngredientsTest, OrdersTest
├── ui/
│   ├── pages/                       — LoginPage, MenuPage
│   └── tests/                       — BaseUiTest, LoginTest, MenuSearchTest
└── utils/                           — ApiProperties, CredentialProperties,
                                       SelenideConfiguration, TokenSpecManager, ConsoleLogger
src/test/resources/
├── application.properties           — all configuration
├── schemas/ingredients-schema.json  — JSON schema of the /ingredients response
└── schemas/orders-page-schema.json  — JSON schema of the /orders response
```

Tests are split into the `ui` and `api` groups.

## Running

```powershell
# all tests
.\mvnw.cmd test

# without a browser (required for CI)
.\mvnw.cmd test "-Dselenide.headless=true"

# UI or API only
.\mvnw.cmd test "-Dgroups=ui"
.\mvnw.cmd test "-Dgroups=api"

# a single class
.\mvnw.cmd test "-Dtest=LoginTest"

# parallel method execution
.\mvnw.cmd test "-Dparallel=methods" "-DthreadCount=2"

# explicit testng.xml suite (order and groups are defined in the file)
.\mvnw.cmd test -Psuite
```

> In PowerShell `-D` arguments must be quoted, otherwise the value is split on dots
> and Maven fails with `Unknown lifecycle phase`.

### Two run modes

| Mode | Command | Details |
|---|---|---|
| Default | `mvn test` | surefire discovers tests itself; `-Dtest=` and `-Dgroups=` work |
| Suite | `mvn test -Psuite` | `testng.xml` takes precedence: tag order, groups and parallelism come from the file; `-Dtest`/`-Dgroups` are ignored |

`testng.xml` is wired through a separate profile on purpose: otherwise surefire stops
understanding `-Dtest=ClassName`, and running a single test becomes inconvenient.
In suite mode parallelism is enabled by the file attributes themselves
(`parallel="tests" thread-count="2"`).

### Allure report

```powershell
.\mvnw.cmd clean test "-Dselenide.headless=true"
.\mvnw.cmd allure:report   # static report in target/allure-report
.\mvnw.cmd allure:serve    # local server with auto-open
```

Always run `clean` before the report: results from previous runs accumulate in
`target/allure-results`, and the report starts showing duplicates.

On the first `allure:report` run the Allure CLI is downloaded via Node.js — it takes
about a minute and is cached in `.allure/`.

What the report contains:

- **Suites** — two top-level branches, `API` and `UI`, then the test class. The long
  `Surefire suite → Surefire test → com.nikanenka...` path is removed in `BaseTest`.
- **Behaviors** — `@Epic` / `@Feature` / `@Story` annotations on test classes.
- **Severity** — `@Severity` marks the critical scenarios.
- **Environment** — stand, API base URI, browser, headless mode, Java and OS, written
  to `environment.properties` before the run.
- **Steps** — Selenide steps with real locators and a screenshot on failure.

### CI and GitHub Pages

`.github/workflows/tests.yml` runs the suite on every push to `master` and on pull
requests: headless tests, Allure report generation, report artifact, and deployment to
GitHub Pages.

Enable the published report once: **Settings → Pages → Build and deployment →
Source: GitHub Actions**. After that the report is available at
`https://<user>.github.io/<repo>/` and is refreshed on every push to `master`.
Pull requests get the report as the `allure-report` artifact.

## Configuration

Everything lives in `src/test/resources/application.properties`, and every value can be
overridden with an environment variable:

| Variable | Default | Purpose |
|---|---|---|
| `API_BASE_URI` | `https://shawarma.threadqa.ru/api/v1` | RestAssured base URI |
| `TEST_USERNAME` | `owner` | username |
| `TEST_PASSWORD` | `owner123` | password |
| `SELENIDE_BASE_URL` | `https://shawarma.threadqa.ru` | UI base URL |
| `SELENIDE_BROWSER` | `chrome` | browser |
| `SELENIDE_HEADLESS` | `false` | headless mode |
| `SELENIDE_TIMEOUT` | `10000` | assertion timeout, ms |
| `SELENIDE_PAGE_LOAD_TIMEOUT` | `30000` | page load timeout, ms |
| `SELENIDE_BROWSER_SIZE` | `1920x1080` | window size |

System properties also work and take precedence, e.g. `-Dselenide.headless=true`.

## Environment requirements

A **JDK 17+** and a configured `JAVA_HOME` are required. Maven takes the JDK from there,
while `java` on `PATH` may be a different one (for example an old 8) — the build will
then fail at compilation.

## Adding a test

**UI:** a page is a Spring bean annotated with `@PageObject`, locators are XPath on
`data-testid`; page objects expose elements and actions only (no assertions), while all
checks live in tests. Methods return `this` for chaining; a test extends `BaseUiTest` and
is marked with `@Test(groups = "ui")`. Navigation uses a relative path, the domain comes
from `selenide.baseUrl`.

**API:** a client is a Spring bean annotated with `@ApiClient` and extending `BaseClient`
(which already provides `tokenSpecManager` and `apiProperties`); a test extends
`BaseApiTest` and is marked with `@Test(groups = "api")`.

![Dog]([https://media.giphy.com/media/3o7abKhOpu0NwenH5O/giphy.gif](https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExOHgzbjBmeTZ0ZjQ3MTFxdmJ0YWFnM3ZyN2c3MXdrbzZjaGltNGY1aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/baNxGhmywafa3h7gPr/giphy.gif))
