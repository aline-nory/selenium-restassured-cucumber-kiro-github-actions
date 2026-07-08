package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.LogUtils;

/**
 * Hooks para cenarios @api.
 */
public class ApiHooks {

    @Before(value = "@api", order = 0)
    public void logScenario(Scenario scenario) {
        LogUtils.info("=== [API] " + scenario.getName() + " ===");
    }
}
