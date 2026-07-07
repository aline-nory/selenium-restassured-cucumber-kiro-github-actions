package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import pages.LoginPage;

/**
 * Step Definitions para os cenários de Login.
 * Credenciais e URLs ficam aqui, fora dos arquivos .feature,
 * seguindo a boa prática de BDD de esconder detalhes técnicos.
 */
public class LoginSteps {

    // Detalhes técnicos centralizados aqui, não expostos no .feature
    private static final String URL_LOGIN    = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private static final String ADMIN_USER   = "admin";
    private static final String ADMIN_PASS   = "admin123";
    private static final String SENHA_ERRADA = "senhaErrada";

    private final LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage(Hooks.getDriver());
    }

    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        loginPage.abrirPagina(URL_LOGIN);
    }

    @Quando("faço login como administrador")
    public void facoLoginComoAdministrador() {
        loginPage.preencherCampo("username", ADMIN_USER);
        loginPage.preencherCampo("password", ADMIN_PASS);
        loginPage.clicarBotaoLogin();
    }

    @Quando("faço login com usuário {string} e senha incorreta")
    public void facoLoginComUsuarioESenhaIncorreta(String usuario) {
        loginPage.preencherCampo("username", usuario);
        loginPage.preencherCampo("password", SENHA_ERRADA);
        loginPage.clicarBotaoLogin();
    }

    @Quando("faço login com usuário {string} e senha {string}")
    public void facoLoginComUsuarioESenha(String usuario, String senha) {
        loginPage.preencherCampo("username", usuario);
        loginPage.preencherCampo("password", senha);
        loginPage.clicarBotaoLogin();
    }

    @Então("devo ser redirecionado para a página inicial")
    public void devoSerRedirecionadoParaPaginaInicial() {
        Assert.assertTrue(
            "Esperava ser redirecionado para o dashboard, mas a URL não contém '/dashboard'",
            loginPage.estaNaPaginaInicial()
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
