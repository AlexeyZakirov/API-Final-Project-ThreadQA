package assertions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AssertableResponse {
    private final ValidatableResponse response;

    public AssertableResponse should(Condition condition) {
        condition.check(response);
        return this;
    }

    public String extractJwtToken() {
        return response.extract().jsonPath().getString("token");
    }

    public <T> T extractAs(Class<T> extractedClass) {
        return response.extract().as(extractedClass);
    }

    public <T> T extractAs(String jsonPath, Class<T> extractedClass) {
        return response.extract().jsonPath().getObject(jsonPath, extractedClass);
    }

    public <T> List<T> extractAsList(Class<T> extractedClass) {
        return response.extract().jsonPath().getList("", extractedClass);
    }

    public <T> List<T> extractAsList(String jsonPath, Class<T> extractedClass) {
        return response.extract().jsonPath().getList(jsonPath, extractedClass);
    }
}
