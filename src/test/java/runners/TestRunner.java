package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner principal.
 *
 * mvn test                                    -> todos
 * mvn test -Dcucumber.filter.tags="@smoke"    -> smoke
 * mvn test -Dcucumber.filter.tags="@api"      -> API
 * mvn test -Dcucumber.filter.tags="@ui"       -> UI
 * mvn test -Denvironment=hml                  -> ambiente HML
 * mvn allure:serve                            -> relatorio
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"steps", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class TestRunner {
}
