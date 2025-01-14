import config.ApiConfig;
import config.ConfigReader;
import config.ProjectConfiguration;
import helpers.RandomUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    private static final ApiConfig apiConfig = ConfigReader.INSTANCE.read();
    protected final RandomUtils random = new RandomUtils();
    protected String login;
    protected String password;

    @BeforeAll
    public static void setUp() {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(apiConfig);
        projectConfiguration.apiConfig();
    }

    @BeforeEach
    public void beforeEach() {
        login = random.generateLogin();
        password = random.generatePassword();
    }
}
