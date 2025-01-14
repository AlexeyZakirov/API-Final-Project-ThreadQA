package services;

import assertions.AssertableResponse;
import io.qameta.allure.Step;
import models.UserAuth;
import models.UserModel;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;

public class UserService {

    @Step("Отправить запрос на регистрацию пользователя")
    public AssertableResponse registerUser(String login, String password) {
        UserModel userModel = UserModel.builder().login(login).pass(password).build();
        return new AssertableResponse(
                given(requestSpec)
                        .body(userModel)
                        .post("/signup")
                        .then()
        );
    }

    @Step("Отправить запрос на получение JWT токена после авторизации")
    public AssertableResponse getJwtToken(String login, String password) {
        UserAuth userAuth = UserAuth.builder()
                .username(login)
                .password(password)
                .build();
        return new AssertableResponse(
                given(requestSpec)
                        .body(userAuth)
                        .post("/login")
                        .then()
        );
    }

    @Step("Отправить запрос о получении данных для пользователя")
    public AssertableResponse getUser(String token) {
        return new AssertableResponse(
                given(requestSpec)
                        .auth().oauth2(token)
                        .get("/user")
                        .then());
    }

    @Step("Поменять пароль пользователя на новый строковый, отправив запрос на смену пароля")
    public AssertableResponse changeUserPassword(String token, String newPassword) {
        Map<String, String> newPasswordMap = new HashMap<>();
        newPasswordMap.put("password", newPassword);

        return new AssertableResponse(
                given(requestSpec)
                        .auth().oauth2(token)
                        .body(newPasswordMap)
                        .put("/user")
                        .then()
        );
    }

    @Step("Поменять пароль пользователя на новый числовой, отправив запрос на смену пароля")
    public AssertableResponse changeUserPassword(String token, Integer newPassword) {
        Map<String, Integer> newPasswordMap = new HashMap<>();
        newPasswordMap.put("password", newPassword);

        return new AssertableResponse(
                given(requestSpec)
                        .auth().oauth2(token)
                        .body(newPasswordMap)
                        .put("/user")
                        .then()
        );
    }

    @Step("Отправить запрос на удаление пользователя")
    public AssertableResponse deleteUser(String token) {
        return new AssertableResponse(
                given(requestSpec)
                        .auth().oauth2(token)
                        .delete("/user")
                        .then()
        );
    }

    @Step("Отправить запрос для получения списка всех пользователей")
    public AssertableResponse getAllUsers() {
        return new AssertableResponse(
                given(requestSpec)
                        .get("/users")
                        .then());
    }
}
