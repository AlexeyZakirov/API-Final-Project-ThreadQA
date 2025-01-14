package services;

import assertions.AssertableResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import models.GamesItem;

import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;

public class GameService {
    @Step("Добавить игру пользователю")
    public AssertableResponse addGameToUser(String token, GamesItem game) {
        return new AssertableResponse(
                given(requestSpec)
                        .contentType(ContentType.JSON)
                        .auth().oauth2(token)
                        .body(game)
                        .post("/api/user/games")
                        .then()
        );
    }

    @Step("Получить список со всеми играми пользователя")
    public AssertableResponse getAllUserGames(String token) {
        return new AssertableResponse(
                given(requestSpec)
                        .auth().oauth2(token)
                        .get("/api/user/games")
                        .then()
        );
    }
}
