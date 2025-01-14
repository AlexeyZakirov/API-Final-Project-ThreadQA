package assertions.conditions;

import assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final Integer expectedStatusCode;

    @Override
    public void check(ValidatableResponse response) {
        Integer realStatusCode = response.extract().statusCode();
        assertThat(expectedStatusCode).isEqualTo(realStatusCode);
    }
}
