package config;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectConfiguration {
    private final ApiConfig apiConfig;

    public void apiConfig(){
        RestAssured.baseURI = apiConfig.baseUrl();
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
