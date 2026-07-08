import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner principal.
 *
 * mvn test                                    -> todos os cenarios
 * mvn test -Dcucumber.filter.tags="@smoke"    -> suite smoke
 * mvn test -Dcucumber.filter.tags="@api"      -> apenas API
 * mvn test -Dcucumber.filter.tags="@ui"       -> apenas UI
 * mvn test -Denvironment=HQA                  -> ambiente HQA
 *
 * Relatorio Allure:
 *   mvn allure:serve                          -> abre no navegador
 *   mvn allure:report                         -> gera em target/site/allure-maven-plugin
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue     = {"steps", "support.hooks"},
    plugin   = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class Runner {
}
