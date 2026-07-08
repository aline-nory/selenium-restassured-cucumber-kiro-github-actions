package support.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import support.environment.Environment;
import support.helpers.LogUtils;
import support.webDriver.DriverFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Hooks para cenarios Web (@ui).
 * Gerencia o ciclo de vida do WebDriver.
 *
 * Screenshot configuravel via config.properties:
 *   screenshot.mode=always        -> captura em sucesso e falha
 *   screenshot.mode=failure_only  -> captura apenas em falha (padrao)
 */
public class WebHooks {

    private DriverFactory driverFactory;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private Environment env;
    private Scenario scenario;

    public WebHooks() {
        env = new Environment();
    }

    @Before(value = "@ui", order = 0)
    public void getProperty(Scenario scenario) {
        this.scenario = scenario;
        LogUtils.printMessage("Iniciando cenario: " + scenario.getName());
    }

    @Before(value = "@ui", order = 1)
    public void launchBrowser() {
        if (getDriver() == null) {
            LogUtils.printMessage("Driver nao inicializado, criando nova instancia...");
            Properties properties = System.getProperties();
            String browserName = properties.getProperty("browser");
            boolean forceBrowser = Boolean.parseBoolean(env.getProperty("forceBrowser"));

            if (browserName == null || forceBrowser) {
                browserName = env.getProperty("browser");
            }

            int implicitTimeout = Integer.parseInt(env.getProperty("timeout.implicit"));
            int pageLoadTimeout = Integer.parseInt(env.getProperty("timeout.pageLoad"));

            driverFactory = new DriverFactory();
            driver.set(driverFactory.init_driver(browserName));
            getDriver().manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
            getDriver().manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        } else {
            LogUtils.printMessage("Driver inicializado, reutilizando instancia...");
        }
    }

    @After(value = "@ui")
    public void tearDown(Scenario scenario) {
        WebDriver d = getDriver();
        if (d == null) return;

        // Captura screenshot baseado na configuracao
        String screenshotMode = env.getProperty("screenshot.mode");
        boolean deveCapturar = "always".equals(screenshotMode) || scenario.isFailed();

        if (deveCapturar && d instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
            String status = scenario.isFailed() ? "FALHA" : "SUCESSO";
            scenario.attach(screenshot, "image/png", status + " - " + scenario.getName());
            LogUtils.printMessage("Screenshot [" + status + "]: " + scenario.getName());
        }

        d.quit();
        driver.remove();
        LogUtils.printMessage("Navegador encerrado.");
    }

    public static WebDriver getDriver() {
        return driver.get();
    }
}
