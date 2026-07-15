package steps.ui;

import config.Environment;
import drivers.DriverManager;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import pages.employeelist.EmployeeListPage;

/**
 * Steps da lista de empregados (UI).
 * Environment injetado via PicoContainer.
 */
public class EmployeeListSteps {

    private final Environment env;
    private EmployeeListPage employeeListPage;

    public EmployeeListSteps(Environment env) {
        this.env = env;
    }

    @Dado("estou na página de lista de empregados")
    public void openEmployeeList() {
        int explicitWait = env.getInt("timeout.explicit", 10);
        employeeListPage = new EmployeeListPage(DriverManager.getDriver(), explicitWait);
        employeeListPage.open(env.getPageUrl("/web/index.php/pim/viewEmployeeList"));
    }

    @Quando("busco pelo nome do empregado {string}")
    public void searchByName(String name) {
        employeeListPage.searchByEmployeeName(name);
    }

    @Quando("clico no botão de busca")
    public void clickSearch() {
        employeeListPage.clickSearch();
    }

    @Quando("clico no botão de adicionar empregado")
    public void clickAdd() {
        employeeListPage.clickAdd();
    }

    @Quando("clico no botão de editar do primeiro empregado")
    public void clickEditFirstRow() {
        employeeListPage.clickEditOnRow(0);
    }

    @Quando("clico no botão de deletar do primeiro empregado")
    public void clickDeleteFirstRow() {
        employeeListPage.clickDeleteOnRow(0);
    }

    @Então("devo ver a tabela de empregados com resultados")
    public void shouldSeeTableWithResults() {
        Assert.assertTrue("Tabela de empregados nao esta visivel", employeeListPage.isTableDisplayed());
        Assert.assertTrue("Tabela nao possui registros", employeeListPage.getTableRowCount() > 0);
    }

    @Então("devo ver a mensagem {string}")
    public void shouldSeeMessage(String expectedMessage) {
        Assert.assertTrue(
                "Mensagem '" + expectedMessage + "' nao exibida",
                employeeListPage.isNoRecordsMessageDisplayed()
        );
    }

    @Então("devo ser redirecionado para a página de adicionar empregado")
    public void shouldBeOnAddEmployeePage() {
        Assert.assertTrue(
                "Nao redirecionou para pagina de adicionar empregado",
                employeeListPage.isOnAddEmployeePage()
        );
    }

    @Então("devo ser redirecionado para a página de detalhes do empregado")
    public void shouldBeOnEmployeeDetailsPage() {
        Assert.assertTrue(
                "Nao redirecionou para detalhes do empregado",
                employeeListPage.isOnEmployeeDetailsPage()
        );
    }
}
