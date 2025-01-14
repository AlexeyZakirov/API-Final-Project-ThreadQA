import helpers.RandomUtils;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    protected final RandomUtils random = new RandomUtils();
    protected String login;
    protected String password;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://85.192.34.140:8080";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeEach
    public void beforeEach() {
        login = random.generateLogin();
        password = random.generatePassword();
    }
}
