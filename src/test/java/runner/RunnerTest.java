package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Runner principal do Cucumber com JUnit.
 *
 * Como executar:
 *   - Pela IDE:    clique com botão direito → Run As → JUnit Test
 *   - Pelo Maven:  mvn test
 *   - Só API:      mvn test -Dcucumber.features=src/test/resources/features/api_posts.feature
 *   - Por tag:     mvn test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    // Todos os arquivos .feature (UI + API)
    features = "src/test/resources/features",

    // Pacote dos Step Definitions e Hooks
    glue = "steps",

    // Relatórios gerados em target/cucumber-reports/
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },

    snippets = CucumberOptions.SnippetType.CAMELCASE,
    monochrome = true
)
public class RunnerTest {

    /**
     * Configura o truststore customizado antes de qualquer teste.
     *
     * Necessário porque o Avast Antivirus intercepta conexões HTTPS e substitui
     * os certificados SSL. O Java 8 rejeita esses certificados por padrão.
     * Ao rodar pela IDE, não há Maven para passar o -D via argLine, então
     * configuramos programaticamente aqui.
     *
     * O arquivo ~/.maven-cacerts foi gerado com o certificado do Avast importado.
     */
    @BeforeClass
    public static void configurarSSL() {
        String cacertsPath = System.getProperty("user.home") + "/.maven-cacerts";
        System.setProperty("javax.net.ssl.trustStore", cacertsPath);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    }
}
