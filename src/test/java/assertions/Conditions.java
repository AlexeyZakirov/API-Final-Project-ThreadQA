package assertions;

import assertions.conditions.MessageCondition;
import assertions.conditions.StatusCodeCondition;
import assertions.conditions.StatusCondition;
import io.qameta.allure.Step;

public class Conditions {

    @Step("Проверить, что в ответе пришло сообщение - {0}")
    public static MessageCondition hasMessage(String textMessage) {
        return new MessageCondition(textMessage);
    }

    @Step("Проверить, что статус код - {0}")
    public static StatusCodeCondition hasStatusCode(Integer expectedStatusCode) {
        return new StatusCodeCondition(expectedStatusCode);
    }

    @Step("Проверить, что статус - {0}")
    public static StatusCondition hasStatus(String statusText) {
        return new StatusCondition(statusText);
    }
}
