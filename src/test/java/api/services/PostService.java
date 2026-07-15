package api.services;

import api.clients.RestClient;
import utils.JsonUtils;

/**
 * Service para o recurso /posts.
 * Encapsula logica de negocio das chamadas API.
 */
public class PostService {

    private final RestClient client;

    public PostService(RestClient client) {
        this.client = client;
    }

    public void listAll() {
        client.get("/posts");
    }

    public void getById(int id) {
        client.get("/posts/" + id);
    }

    public void getByUser(int userId) {
        client.get("/posts?userId=" + userId);
    }

    public void prepareCreate() {
        String body = JsonUtils.load("payloads/posts/create-post.json");
        client.setBody(body);
    }

    public void submitCreate() {
        client.post("/posts");
    }

    public void prepareUpdate(int id) {
        String rawBody = JsonUtils.load("payloads/posts/update-post.json");
        String body = JsonUtils.setIntField(rawBody, "id", id);
        client.setBody(body);
    }

    public void submitUpdate(int id) {
        client.put("/posts/" + id);
    }

    public void delete(int id) {
        client.delete("/posts/" + id);
    }
}
