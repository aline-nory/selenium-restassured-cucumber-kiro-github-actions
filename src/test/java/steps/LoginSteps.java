package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import pages.LoginPage;

/**
 * Step Definitions para os cenários de Login.
 * Cada método mapeia um passo do arquivo .feature.
 */
public class LoginSteps {

    private final LoginPage loginPage;

    // URL do sistema sob teste (OrangeHRM demo)
    private static final String URL_LOGIN = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    public LoginSteps() {
        // Obtém o WebDriver gerenciado pelo hook de ciclo de vida
        this.loginPage = new LoginPage(Hooks.getDriver());
    }

    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        loginPage.abrirPagina(URL_LOGIN);
    }

    @Quando("preencho o campo {string} com {string}")
    public void preenchoOCampoComValor(String campo, String valor) {
        loginPage.preencherCampo(campo, valor);
    }

    @E("clico no botão de login")
    public void clicoNoBotaoDeLogin() {
        loginPage.clicarBotaoLogin();
    }

    @Então("devo ser redirecionado para a página inicial")
    public void devoSerRedirecionadoParaPaginaInicial() {
        Assert.assertTrue(
            "Esperava ser redirecionado para o dashboard, mas a URL não contém '/dashboard'",
            loginPage.estaNaPaginaInicial()
        );
    }

    @E("devo ver a mensagem de boas-vindas {string}")
    public void devoVerMensagemBoasVindas(String mensagemEsperada) {
        String mensagemAtual = loginPage.obterMensagemBoasVindas();
        Assert.assertTrue(
            "Mensagem de boas-vindas esperada: '" + mensagemEsperada + "', mas encontrada: '" + mensagemAtual + "'",
            mensagemAtual.contains(mensagemEsperada)
        );
    }

    @Então("devo ver a mensagem de erro {string}")
    public void devoVerMensagemDeErro(String mensagemEsperada) {
        String mensagemAtual = loginPage.obterMensagemErro();
        Assert.assertEquals(
            "Mensagem de erro não corresponde ao esperado",
            mensagemEsperada,
            mensagemAtual
        );
    }
}
