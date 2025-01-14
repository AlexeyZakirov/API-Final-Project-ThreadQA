package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:project.properties"
})
public interface ApiConfig extends Config {
    String baseUrl();
}
