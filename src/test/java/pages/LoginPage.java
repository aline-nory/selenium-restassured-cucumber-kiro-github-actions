package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para a página de Login.
 * Encapsula os elementos e ações da tela de login.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By campoUsuario    = By.name("username");
    private final By campoSenha      = By.name("password");
    private final By botaoLogin      = By.cssSelector("button[type='submit']");
    private final By mensagemErro    = By.cssSelector(".oxd-alert-content-text");
    private final By mensagemBoasVindas = By.cssSelector(".oxd-userdropdown-name");
    private final By tituloPagina    = By.cssSelector(".oxd-text--h6");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Selenium 3: WebDriverWait recebe timeout em segundos (long)
        this.wait = new WebDriverWait(driver, 10);
    }

    /** Abre a URL da página de login. */
    public void abrirPagina(String url) {
        driver.get(url);
    }

    /** Preenche um campo de input pelo seu atributo name ou placeholder. */
    public void preencherCampo(String nomeCampo, String valor) {
        By locator = By.name(nomeCampo);
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        campo.clear();
        campo.sendKeys(valor);
    }

    /** Clica no botão de submit do formulário de login. */
    public void clicarBotaoLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoLogin)).click();
    }

    /** Retorna o texto da mensagem de erro exibida após login inválido. */
    public String obterMensagemErro() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemErro)).getText();
    }

    /** Retorna o texto de boas-vindas exibido após login bem-sucedido. */
    public String obterMensagemBoasVindas() {
        // Aguarda o dropdown de usuario no topo da pagina apos login
        return wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemBoasVindas)).getText();
    }

    /** Verifica se o usuário foi redirecionado para a página inicial (dashboard). */
    public boolean estaNaPaginaInicial() {
        try {
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
