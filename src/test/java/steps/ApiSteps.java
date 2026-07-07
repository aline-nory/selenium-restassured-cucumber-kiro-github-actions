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

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Step Definitions para os cenários de teste de API.
 * URL base e dados de requisição ficam aqui, fora dos arquivos .feature,
 * seguindo a boa prática de BDD de esconder detalhes de infraestrutura.
 */
public class ApiSteps {

    // Detalhes de infraestrutura centralizados aqui, não expostos no .feature
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private RequestSpecification request;
    private Response response;

    // -------------------------------------------------------------------------
    // CONTEXTO / CONFIGURAÇÃO
    // -------------------------------------------------------------------------

    @Dado("que estou consumindo a API de posts")
    public void queEstouConsumindoAApiDePosts() {
        RestAssured.baseURI = BASE_URL;
        request = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }

    @Dado("que tenho os dados de um novo post")
    public void queTenhoOsDadosDeUmNovoPost() {
        String corpo = "{"
                + "\"title\": \"Post de Teste Automatizado\","
                + "\"body\": \"Conteudo criado via REST Assured + Cucumber\","
                + "\"userId\": 1"
                + "}";
        request = request.body(corpo);
    }

    @Dado("que tenho os dados de atualização do post {int}")
    public void queTenhoOsDadosDeAtualizacaoDoPost(int id) {
        String corpo = "{"
                + "\"id\": " + id + ","
                + "\"title\": \"Titulo Atualizado\","
                + "\"body\": \"Corpo atualizado via REST Assured\","
                + "\"userId\": 1"
                + "}";
        request = request.body(corpo);
    }

    // -------------------------------------------------------------------------
    // AÇÕES (WHEN)
    // -------------------------------------------------------------------------

    @Quando("busco todos os posts")
    public void buscoTodosOsPosts() {
        response = request.when().get("/posts")
                .then().log().status().log().body()
                .extract().response();
    }

    @Quando("busco o post de ID {int}")
    public void buscoOPostDeId(int id) {
        response = request.when().get("/posts/" + id)
                .then().log().status().log().body()
                .extract().response();
    }

    @Quando("busco os posts do usuário {int}")
    public void buscoOsPostsDoUsuario(int userId) {
        response = request.when().get("/posts?userId=" + userId)
                .then().log().status().log().body()
                .extract().response();
    }

    @Quando("envio o novo post")
    public void envioONovoPost() {
        response = request.when().post("/posts")
                .then().log().status().log().body()
                .extract().response();
    }

    @Quando("atualizo o post {int}")
    public void atualizoOPost(int id) {
        response = request.when().put("/posts/" + id)
                .then().log().status().log().body()
                .extract().response();
    }

    @Quando("deleto o post {int}")
    public void deletoOPost(int id) {
        response = request.when().delete("/posts/" + id)
                .then().log().status().log().body()
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
        Assert.assertEquals("Quantidade de posts incorreta", quantidadeEsperada, tamanho);
    }

    @E("o campo {string} deve ter valor inteiro {int}")
    public void oCampoDeveTerValorInteiro(String campo, int valorEsperado) {
        int valorAtual = response.jsonPath().getInt(campo);
        Assert.assertEquals("Valor do campo '" + campo + "' incorreto", valorEsperado, valorAtual);
    }

    @E("o campo {string} deve ter valor de texto {string}")
    public void oCampoDeveTerValorDeTexto(String campo, String valorEsperado) {
        String valorAtual = response.jsonPath().getString(campo);
        Assert.assertEquals("Valor do campo '" + campo + "' incorreto", valorEsperado, valorAtual);
    }

    @E("o campo {string} não deve estar vazio")
    public void oCampoNaoDeveEstarVazio(String campo) {
        Object valor = response.jsonPath().get(campo);
        Assert.assertNotNull("Campo '" + campo + "' está nulo", valor);
        Assert.assertNotEquals("Campo '" + campo + "' está vazio", "", valor.toString().trim());
    }

    @E("todos os posts devem ter {string} igual a {int}")
    public void todosOsPostsDevemTerIgualA(String campo, int valorEsperado) {
        List<Integer> valores = response.jsonPath().getList(campo, Integer.class);
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
