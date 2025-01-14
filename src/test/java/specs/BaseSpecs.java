package specs;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class BaseSpecs {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON);
}
