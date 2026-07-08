package support.communication;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import support.helpers.LogUtils;

import static io.restassured.RestAssured.given;

/**
 * Classe para comunicacao REST.
 * Cada instancia mantém seu próprio estado (request/response),
 * permitindo execução paralela sem conflitos.
 */
public class RestApi {

    private Response response;
    private RequestSpecification request;

    public void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
        request = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public void setBody(String body) {
        request = request.body(body);
    }

    public void get(String endpoint) {
        LogUtils.printMessage("GET " + RestAssured.baseURI + endpoint);
        response = request.when().get(endpoint).then().extract().response();
    }

    public void post(String endpoint) {
        LogUtils.printMessage("POST " + RestAssured.baseURI + endpoint);
        response = request.when().post(endpoint).then().extract().response();
    }

    public void put(String endpoint) {
        LogUtils.printMessage("PUT " + RestAssured.baseURI + endpoint);
        response = request.when().put(endpoint).then().extract().response();
    }

    public void delete(String endpoint) {
        LogUtils.printMessage("DELETE " + RestAssured.baseURI + endpoint);
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
