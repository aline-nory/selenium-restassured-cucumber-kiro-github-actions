package pages.employeelist;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;
import utils.LogUtils;

import java.util.List;

/**
 * Page Object da pagina Employee List (PIM).
 */
public class EmployeeListPage extends BasePage {

    private final By employeeNameInput = By.cssSelector(".oxd-autocomplete-wrapper input[placeholder='Type for hints...']");
    private final By searchButton      = By.cssSelector("button[type='submit']");
    private final By addButton         = By.cssSelector(".orangehrm-header-container .oxd-button--secondary");
    private final By tableRows         = By.cssSelector(".oxd-table-body .oxd-table-row");
    private final By noRecordsMessage  = By.cssSelector(".orangehrm-horizontal-padding span");
    private final By editButtons       = By.cssSelector(".oxd-table-body .oxd-table-row .oxd-icon.bi-pencil-fill");
    private final By deleteButtons     = By.cssSelector(".oxd-table-body .oxd-table-row .oxd-icon.bi-trash");

    private final By loadingSpinner    = By.cssSelector(".oxd-loading-spinner");

    public EmployeeListPage(WebDriver driver, int explicitWaitSeconds) {
        super(driver, explicitWaitSeconds);
    }

    public void open(String url) {
        navigate(url);
        waitUntilLoaded();
    }

    public void searchByEmployeeName(String name) {
        type(employeeNameInput, name);
    }

    public void clickSearch() {
        click(searchButton);
        waitForSearchResults();
    }

    /**
     * Aguarda o spinner de loading desaparecer apos submeter uma busca.
     * O OrangeHRM destroi e recria a tabela via AJAX durante a busca.
     */
    private void waitForSearchResults() {
        try {
            // Espera o spinner aparecer (pode ser muito rapido e nem aparecer)
            wait.until(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
        } catch (TimeoutException e) {
            // Spinner ja sumiu ou nunca apareceu — ok, resultado ja carregou
        }
        try {
            // Espera o spinner sumir (resultados prontos)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (TimeoutException e) {
            LogUtils.warn("Timeout aguardando spinner de busca desaparecer");
        }
    }

    public void clickAdd() {
        click(addButton);
    }

    public int getTableRowCount() {
        try {
            List<WebElement> rows = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
            return rows.size();
        } catch (TimeoutException e) {
            LogUtils.warn("Timeout aguardando linhas da tabela ficarem visiveis");
            return 0;
        }
    }

    public boolean isNoRecordsMessageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsMessage));
            return element.getText().contains("No Records Found");
        } catch (TimeoutException e) {
            LogUtils.warn("Timeout aguardando mensagem 'No Records Found'");
            return false;
        }
    }

    public boolean isTableDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
            return true;
        } catch (TimeoutException e) {
            LogUtils.warn("Timeout aguardando tabela de empregados ficar visivel");
            return false;
        }
    }

    public void clickEditOnRow(int rowIndex) {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(editButtons));
        if (rowIndex < buttons.size()) {
            buttons.get(rowIndex).click();
        } else {
            throw new IndexOutOfBoundsException(
                    "Row index " + rowIndex + " fora do range. Total de linhas com edit: " + buttons.size());
        }
    }

    public void clickDeleteOnRow(int rowIndex) {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(deleteButtons));
        if (rowIndex < buttons.size()) {
            buttons.get(rowIndex).click();
        } else {
            throw new IndexOutOfBoundsException(
                    "Row index " + rowIndex + " fora do range. Total de linhas com delete: " + buttons.size());
        }
    }

    public boolean isOnEmployeeListPage() {
        return urlContains("/pim/viewEmployeeList");
    }

    public boolean isOnAddEmployeePage() {
        return urlContains("/pim/addEmployee");
    }

    public boolean isOnEmployeeDetailsPage() {
        return urlContains("/pim/viewPersonalDetails");
    }

    /**
     * Confirma que a pagina de Employee List carregou (campo de busca visivel).
     */
    private void waitUntilLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "EmployeeListPage nao carregou: campo de busca nao ficou visivel dentro do timeout", e);
        }
    }
}
