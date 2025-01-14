package config;

import org.aeonbits.owner.ConfigFactory;

public enum ConfigReader {
    INSTANCE;

    private static final ApiConfig apiConfig =
            ConfigFactory.create(
                    ApiConfig.class,
                    System.getProperties()
            );

    public ApiConfig read(){
        return apiConfig;
    }
}
