package runner;

import config.SslConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Runner principal do Cucumber com JUnit.
 *
 * Como executar:
 *   - Pelo Maven:  mvn test
 *   - Só API:      mvn test -Dcucumber.features=src/test/resources/features/api_posts.feature
 *   - Por tag:     mvn test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue     = { "steps", "config" },
    plugin   = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    snippets  = CucumberOptions.SnippetType.CAMELCASE,
    monochrome = true
)
public class RunnerTest {

    @BeforeClass
    public static void configurarAmbiente() {
        SslConfig.configurar();
    }
}
