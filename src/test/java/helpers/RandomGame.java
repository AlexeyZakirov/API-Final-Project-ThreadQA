package helpers;

import com.github.javafaker.Faker;
import models.DlcsItem;
import models.GamesItem;
import models.Requirements;
import models.SimilarDlc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RandomGame {
    public static GamesItem createRandomGame() {
        Faker faker = new Faker();
        SimilarDlc similarDlc = new SimilarDlc(faker.random().nextBoolean(), faker.pokemon().name());
        DlcsItem dlcsItem = DlcsItem.builder()
                .isDlcFree(false)
                .dlcName(faker.pokemon().name())
                .rating(faker.number().numberBetween(0, 10))
                .description(faker.harryPotter().book())
                .price(faker.number().numberBetween(0, 1000))
                .similarDlc(similarDlc)
                .build();
        List<DlcsItem> dlcsItemList = List.of(dlcsItem);
        Requirements requirements = Requirements.builder()
                .osName(faker.letterify("???", true))
                .ramGb(faker.number().numberBetween(0, 10))
                .hardDrive(faker.number().numberBetween(0, 10))
                .videoCard(faker.letterify("??????"))
                .build();
        List<String> tagsList = List.of(faker.pokemon().name(), faker.pokemon().name(), faker.pokemon().name());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = now.format(formatter);

        return GamesItem.builder()
                .gameId(faker.number().numberBetween(0, 99999))
                .title(faker.pokemon().name())
                .genre(faker.letterify("??????"))
                .requiredAge(faker.random().nextBoolean())
                .isFree(false)
                .price(faker.number().numberBetween(0, 99999))
                .company(faker.harryPotter().character())
                .publishDate(formattedDate)
                .rating(faker.number().numberBetween(0, 10))
                .description(faker.starTrek().location())
                .tags(tagsList)
                .dlcs(dlcsItemList)
                .requirements(requirements)
                .build();
    }
}
