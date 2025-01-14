import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import services.UserService;

import java.util.List;

import static assertions.Conditions.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("user_test")
@Owner("Alexey Zakirov")
@DisplayName("Проверки пользовательского контроллера")
public class UserControllerTests extends TestBase {
    private final UserService userService = new UserService();

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание нового пользователя")
    @Test
    public void registerNewUserTest() {

        userService.registerUser(login, password)
                .should(hasMessage("User created"))
                .should(hasStatusCode(201))
                .should(hasStatus("success"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        UserModel userAfterRegister = userService.getUser(token)
                .should(hasStatusCode(200))
                .extractAs(UserModel.class);

        step("Проверить, что логин при регистрации такой же, как и после получения информации о юзере", x -> {
            assertThat(userAfterRegister.getLogin()).isEqualTo(login);
        });
        step("Проверить, что пароль при регистрации такой же, как и после получения информации о юзере", x -> {
            assertThat(userAfterRegister.getPass()).isEqualTo(password);
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание пользователя, который уже существует в системе")
    @Test
    public void registerExistedUserTest() {

        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));


        userService.registerUser(login, password)
                .should(hasStatusCode(400))
                .should(hasStatus("fail"))
                .should(hasMessage("Login already exist"));
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Ввод неверного пароля при получении JWT токена")
    @Test
    public void loginWithWrongPasswordTest() {

        String wrongPassword = "wrongPass";

        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));

        step("Отправить запрос на получение JWT токена с неверным паролем в теле запроса", x -> {
            userService.getJwtToken(login, wrongPassword)
                    .should(hasStatusCode(401));
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешное обновление пароля у созданного пользователя")
    @Test
    public void successfulChangeUserPasswordTest() {

        String newPassword = random.generatePassword();

        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        userService.changeUserPassword(token, newPassword)
                .should(hasStatusCode(200))
                .should(hasStatus("success"))
                .should(hasMessage("User password successfully changed"));

        UserModel userAfterChangePassword = userService.getUser(token)
                .should(hasStatusCode(200))
                .extractAs(UserModel.class);

        step("Проверить, что пароль изменен на новый, который отправлялся в запросе изменения пароля", x -> {
            assertThat(userAfterChangePassword.getPass()).isEqualTo(newPassword);
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Изменение пароля на null")
    @Test
    public void changePasswordNullTest() {
        String newPassword = null;

        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        userService.changeUserPassword(token, newPassword)
                .should(hasStatusCode(400))
                .should(hasStatus("fail"))
                .should(hasMessage("Body has no password parameter"));
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Изменение пароля на integer")
    @Test
    public void changePasswordIntegerTest() {

        Integer newPassword = random.generateRandomInt(2, 10);

        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        userService.changeUserPassword(token, newPassword)
                .should(hasStatusCode(200))
                .should(hasStatus("success"))
                .should(hasMessage("User password successfully changed"));

        UserModel userAfterChangePassword = userService.getUser(token)
                .should(hasStatusCode(200))
                .extractAs(UserModel.class);

        step("Проверить, что пароль изменен на новый, который отправлялся в запросе изменения пароля", x -> {
            assertThat(userAfterChangePassword.getPass()).isEqualTo(newPassword.toString());
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешное удаление существующего пользователя")
    @Test
    public void deleteExistUserTest() {
        userService.registerUser(login, password)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("User created"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasStatus("success"))
                .should(hasMessage("User successfully deleted"));

        step("Проверить, что невозможно авторизоваться под удаленным пользователем", x -> {
            userService.getJwtToken(login, password)
                    .should(hasStatusCode(401));
        });

        List<String> allUsers = userService.getAllUsers().extractAsList(String.class);

        step("Проверить, что в общем списке пользователей отсутствует удалённый пользователь", x -> {
            assertThat(allUsers.stream().filter(user -> user.equals(login)))
                    .isEmpty();
        });
    }
}
