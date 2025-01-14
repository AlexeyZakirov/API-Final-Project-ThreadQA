package assertions.conditions;

import assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import models.Info;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String messageText;

    @Override
    public void check(ValidatableResponse response) {
        Info info = response.extract().jsonPath().getObject("info", Info.class);
        assertThat(info.getMessage()).isEqualTo(messageText);
    }
}
