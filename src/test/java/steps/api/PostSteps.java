package steps.api;

import api.clients.RestClient;
import api.services.PostService;
import config.Environment;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.util.List;

/**
 * Steps de API Posts.
 * Todas as dependencias injetadas via PicoContainer.
 */
public class PostSteps {

    private final Environment env;
    private final RestClient restClient;
    private final PostService postService;

    public PostSteps(Environment env, RestClient restClient, PostService postService) {
        this.env = env;
        this.restClient = restClient;
        this.postService = postService;
    }

    @Dado("que estou consumindo a API de posts")
    public void setupApi() {
        restClient.setBaseUri(env.apiBaseUrl);
    }

    @Dado("que tenho os dados de um novo post")
    public void prepareNewPost() {
        postService.prepareCreate();
    }

    @Dado("que tenho os dados de atualização do post {int}")
    public void prepareUpdate(int id) {
        postService.prepareUpdate(id);
    }

    @Quando("busco todos os posts")
    public void getAll() {
        postService.listAll();
    }

    @Quando("busco o post de ID {int}")
    public void getById(int id) {
        postService.getById(id);
    }

    @Quando("busco os posts do usuário {int}")
    public void getByUser(int userId) {
        postService.getByUser(userId);
    }

    @Quando("envio o novo post")
    public void submitPost() {
        postService.submitCreate();
    }

    @Quando("atualizo o post {int}")
    public void updatePost(int id) {
        postService.submitUpdate(id);
    }

    @Quando("deleto o post {int}")
    public void deletePost(int id) {
        postService.delete(id);
    }

    @Então("o status code da resposta deve ser {int}")
    public void validateStatus(int expected) {
        Assert.assertEquals("Status incorreto", expected, restClient.getStatusCode());
    }

    @E("o Content-Type da resposta deve conter {string}")
    public void validateContentType(String expected) {
        Assert.assertTrue("Content-Type incorreto", restClient.getContentType().contains(expected));
    }

    @E("a resposta deve conter {int} posts")
    public void validateCount(int expected) {
        Assert.assertEquals("Quantidade incorreta", expected,
                restClient.getResponse().jsonPath().getList("$").size());
    }

    @E("o campo {string} deve ter valor inteiro {int}")
    public void validateIntField(String field, int expected) {
        Assert.assertEquals("Campo '" + field + "' incorreto", expected,
                restClient.getResponse().jsonPath().getInt(field));
    }

    @E("o campo {string} deve ter valor de texto {string}")
    public void validateTextField(String field, String expected) {
        Assert.assertEquals("Campo '" + field + "' incorreto", expected,
                restClient.getResponse().jsonPath().getString(field));
    }

    @E("o campo {string} não deve estar vazio")
    public void validateNotEmpty(String field) {
        Object value = restClient.getResponse().jsonPath().get(field);
        Assert.assertNotNull("Campo nulo", value);
        Assert.assertNotEquals("Campo vazio", "", value.toString().trim());
    }

    @E("todos os posts devem ter {string} igual a {int}")
    public void validateAllFields(String field, int expected) {
        List<Integer> values = restClient.getResponse().jsonPath().getList(field, Integer.class);
        Assert.assertFalse("Lista vazia", values.isEmpty());
        for (int i = 0; i < values.size(); i++) {
            Assert.assertEquals("Post[" + i + "] incorreto", expected, (int) values.get(i));
        }
    }

    @E("a resposta deve estar de acordo com o schema {string}")
    public void validateSchema(String schemaFile) {
        restClient.getResponse().then().assertThat()
                .body(io.restassured.module.jsv.JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schemas/" + schemaFile));
    }
}
