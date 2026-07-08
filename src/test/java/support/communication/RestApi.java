package support.communication;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import support.helpers.LogUtils;

import static io.restassured.RestAssured.given;

/**
 * Classe para comunicacao REST.
 * Cada instancia mantém seu proprio estado (request/response).
 * Usa baseUri por instancia (nao global) para seguranca em execucao paralela.
 */
public class RestApi {

    private Response response;
    private RequestSpecification request;
    private String baseUri;

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        request = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public void setBody(String body) {
        request = request.body(body);
    }

    public void get(String endpoint) {
        LogUtils.printMessage("GET " + baseUri + endpoint);
        response = request.when().get(endpoint).then().extract().response();
    }

    public void post(String endpoint) {
        LogUtils.printMessage("POST " + baseUri + endpoint);
        response = request.when().post(endpoint).then().extract().response();
    }

    public void put(String endpoint) {
        LogUtils.printMessage("PUT " + baseUri + endpoint);
        response = request.when().put(endpoint).then().extract().response();
    }

    public void delete(String endpoint) {
        LogUtils.printMessage("DELETE " + baseUri + endpoint);
        response = request.when().delete(endpoint).then().extract().response();
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getContentType() {
        return response.getContentType();
    }

    public Response getResponse() {
        return response;
    }
}
