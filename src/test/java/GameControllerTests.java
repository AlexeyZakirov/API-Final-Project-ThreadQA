import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.GamesItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import services.GameService;
import services.UserService;

import java.util.List;

import static assertions.Conditions.*;
import static helpers.RandomGame.createRandomGame;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("game_test")
@Owner("Alexey Zakirov")
@DisplayName("Проверки игрового контроллера")
public class GameControllerTests extends TestBase {
    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Добавление рандомно сгенерированной игры пользователю")
    @Test
    public void addRandomGameTest() {
        userService.registerUser(login, password)
                .should(hasMessage("User created"))
                .should(hasStatusCode(201))
                .should(hasStatus("success"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        GamesItem randomGamesItem = createRandomGame();

        GamesItem addedGamesItem = gameService.addGameToUser(token, randomGamesItem)
                .should(hasStatusCode(201))
                .should(hasStatus("success"))
                .should(hasMessage("Game created"))
                .extractAs("register_data", GamesItem.class);

        step("Проверить, что название игры совпадает с тем, что было в запросе на добавление игры", x -> {
            assertThat(addedGamesItem.getTitle()).isEqualTo(randomGamesItem.getTitle());
        });

        List<GamesItem> gamesFromUserStorage = gameService.getAllUserGames(token)
                .should(hasStatusCode(200))
                .extractAsList(GamesItem.class);

        step("Проверить, что название игры в списке пользователей совпадает с тем, что был в запросе на добавление игры",
                x -> {
                    assertThat(gamesFromUserStorage.get(0).getTitle()).isEqualTo(addedGamesItem.getTitle());
                });
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка наличия ошибки при добавлении игры пользователю выше лимита по количеству игр")
    @Test
    public void limitSizeListOfGamesTest() {
        userService.registerUser(login, password)
                .should(hasMessage("User created"))
                .should(hasStatusCode(201))
                .should(hasStatus("success"));

        String token = userService.getJwtToken(login, password)
                .should(hasStatusCode(200))
                .extractJwtToken();

        step("Добавить максимально возможное значение игр пользователю", x -> {
            for (int i = 0; i < 21; i++) {
                gameService.addGameToUser(token, createRandomGame())
                        .should(hasStatusCode(201))
                        .should(hasStatus("success"))
                        .should(hasMessage("Game created"));
            }
            ;
        });

        step("Проверить, что нельзя добавить игру выше лимита", x -> {

            gameService.addGameToUser(token, createRandomGame())
                    .should(hasStatusCode(400))
                    .should(hasStatus("fail"))
                    .should(hasMessage("Limit of games, user can have only 20 games"));
        });
    }
}
