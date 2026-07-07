package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Step Definitions para os cenários de teste de API com REST Assured.
 * Utiliza a API pública JSONPlaceholder (https://jsonplaceholder.typicode.com).
 */
public class ApiSteps {

    private RequestSpecification request;
    private Response response;

    // -------------------------------------------------------------------------
    // CONTEXTO / CONFIGURAÇÃO
    // -------------------------------------------------------------------------

    @Dado("que a URL base da API é {string}")
    public void queAUrlBaseDaApiE(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        // Prepara uma nova requisição limpa para cada cenário
        request = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all(); // loga headers e body no console para facilitar debug
    }

    @Dado("que tenho o seguinte corpo de requisição:")
    public void queTenhoOSeguinteCorpoDeRequisicao(String body) {
        request = request.body(body);
    }

    // -------------------------------------------------------------------------
    // AÇÕES (WHEN)
    // -------------------------------------------------------------------------

    @Quando("faço uma requisição GET para {string}")
    public void facoUmaRequisicaoGetPara(String endpoint) {
        response = request
                .when()
                .get(endpoint)
                .then()
                .log().status().log().body() // loga status e body da resposta
                .extract().response();
    }

    @Quando("faço uma requisição POST para {string}")
    public void facoUmaRequisicaoPostPara(String endpoint) {
        response = request
                .when()
                .post(endpoint)
                .then()
                .log().status().log().body()
                .extract().response();
    }

    @Quando("faço uma requisição PUT para {string}")
    public void facoUmaRequisicaoPutPara(String endpoint) {
        response = request
                .when()
                .put(endpoint)
                .then()
                .log().status().log().body()
                .extract().response();
    }

    @Quando("faço uma requisição DELETE para {string}")
    public void facoUmaRequisicaoDeletePara(String endpoint) {
        response = request
                .when()
                .delete(endpoint)
                .then()
                .log().status().log().body()
                .extract().response();
    }

    // -------------------------------------------------------------------------
    // VERIFICAÇÕES (THEN / AND)
    // -------------------------------------------------------------------------

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCodeEsperado) {
        Assert.assertEquals(
                "Status code incorreto",
                statusCodeEsperado,
                response.getStatusCode()
        );
    }

    @E("o Content-Type da resposta deve conter {string}")
    public void oContentTypeDaRespostaDeveConter(String contentTypeEsperado) {
        String contentType = response.getContentType();
        Assert.assertTrue(
                "Content-Type esperado: '" + contentTypeEsperado + "', recebido: '" + contentType + "'",
                contentType.contains(contentTypeEsperado)
        );
    }

    @E("a resposta deve conter {int} posts")
    public void aRespostaDeveConterPosts(int quantidadeEsperada) {
        int tamanho = response.jsonPath().getList("$").size();
        Assert.assertEquals(
                "Quantidade de posts incorreta",
                quantidadeEsperada,
                tamanho
        );
    }

    @E("o campo {string} deve ter valor inteiro {int}")
    public void oCampoDeveTerValorInteiro(String campo, int valorEsperado) {
        int valorAtual = response.jsonPath().getInt(campo);
        Assert.assertEquals(
                "Valor do campo '" + campo + "' incorreto",
                valorEsperado,
                valorAtual
        );
    }

    @E("o campo {string} deve ter valor de texto {string}")
    public void oCampoDeveTerValorDeTexto(String campo, String valorEsperado) {
        String valorAtual = response.jsonPath().getString(campo);
        Assert.assertEquals(
                "Valor do campo '" + campo + "' incorreto",
                valorEsperado,
                valorAtual
        );
    }

    @E("o campo {string} não deve estar vazio")
    public void oCampoNaoDeveEstarVazio(String campo) {
        Object valor = response.jsonPath().get(campo);
        Assert.assertNotNull("Campo '" + campo + "' está nulo", valor);
        Assert.assertNotEquals("Campo '" + campo + "' está vazio", "", valor.toString().trim());
    }

    @E("todos os posts devem ter {string} igual a {int}")
    public void todosOsPostsDevemTerIgualA(String campo, int valorEsperado) {
        // Extrai todos os valores do campo em todos os objetos da lista raiz
        java.util.List<Integer> valores = response.jsonPath().getList(campo, Integer.class);
        Assert.assertFalse("A lista de posts está vazia", valores.isEmpty());
        for (int i = 0; i < valores.size(); i++) {
            Assert.assertEquals(
                    "Post[" + i + "] campo '" + campo + "' incorreto",
                    valorEsperado,
                    (int) valores.get(i)
            );
        }
    }
}
