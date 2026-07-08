package services;

import support.communication.RestApi;
import support.helpers.JsonTemplate;

/**
 * Classe de servico para o endpoint /posts.
 * Encapsula as chamadas de API relacionadas a Posts.
 * Os payloads JSON ficam em src/test/resources/template.api/.
 */
public class PostService {

    private final RestApi restApi;

    public PostService(RestApi restApi) {
        this.restApi = restApi;
    }

    public void listarTodos() {
        restApi.get("/posts");
    }

    public void buscarPorId(int id) {
        restApi.get("/posts/" + id);
    }

    public void buscarPorUsuario(int userId) {
        restApi.get("/posts?userId=" + userId);
    }

    public void criarPost() {
        String body = JsonTemplate.load("POST_CriarPost.json");
        restApi.setBody(body);
        restApi.post("/posts");
    }

    public void atualizarPost(int id) {
        String body = JsonTemplate.load("PUT_AtualizarPost.json")
                .replace("\"id\":1", "\"id\":" + id);
        restApi.setBody(body);
        restApi.put("/posts/" + id);
    }

    public void deletarPost(int id) {
        restApi.delete("/posts/" + id);
    }
}
