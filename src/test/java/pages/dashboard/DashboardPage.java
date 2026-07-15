package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;
import utils.LogUtils;

/**
 * Page Object mínimo da página Dashboard.
 * Responsável apenas por validar que o usuário chegou ao dashboard após login.
 */
public class DashboardPage extends BasePage {

    private final By dashboardHeading = By.cssSelector(".oxd-topbar-header-breadcrumb h6");

    public DashboardPage(WebDriver driver, int explicitWaitSeconds) {
        super(driver, explicitWaitSeconds);
    }

    /**
     * Verifica se o usuario esta no dashboard (URL + elemento ancora visivel).
     * Retorna boolean — para uso em assertions de @Então.
     */
    public boolean isOnDashboard() {
        return urlContains("/dashboard") && waitUntilLoaded();
    }

    /**
     * Confirma que o dashboard carregou, lancando excecao se nao.
     * Para uso em steps @Dado (pre-condicao): se o login falhou,
     * o cenario deve abortar com mensagem clara em vez de cascatear.
     */
    public void ensureLoaded() {
        if (!urlContains("/dashboard")) {
            throw new IllegalStateException(
                    "Login falhou: URL nao contem '/dashboard'. URL atual: " + driver.getCurrentUrl());
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeading));
        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "Dashboard nao renderizou: heading nao ficou visivel dentro do timeout", e);
        }
    }

    /**
     * Aguarda o heading do dashboard ficar visivel, confirmando que a pagina renderizou.
     */
    private boolean waitUntilLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeading));
            return true;
        } catch (TimeoutException e) {
            LogUtils.warn("Timeout aguardando heading do dashboard ficar visivel");
            return false;
        }
    }
}
