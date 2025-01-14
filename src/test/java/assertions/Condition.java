package assertions;

import io.restassured.response.ValidatableResponse;

public interface Condition {
    public void check(ValidatableResponse response);
}
