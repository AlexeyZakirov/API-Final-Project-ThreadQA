package helpers;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class RandomUtils {
    Faker faker = new Faker();

    @Step("Сгенерировать логин для нового пользователя")
    public String generateLogin() {
        return faker.name().name();
    }

    @Step("Сгенерировать пароль для нового пользователя")
    public String generatePassword() {
        return faker.internet().password(true);
    }

    @Step("Сгенерировать числовое значение")
    public int generateRandomInt(int min, int max) {
        return faker.number().numberBetween(min, max);
    }
}
