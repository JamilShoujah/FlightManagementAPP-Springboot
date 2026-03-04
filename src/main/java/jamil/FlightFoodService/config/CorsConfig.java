package jamil.FlightFoodService.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    private static final Logger log = LoggerFactory.getLogger(CorsConfig.class);

    private static final String DEFAULT_ALLOWED_ORIGINS =
            "http://localhost:4200,http://127.0.0.1:4200";

    private final String[] allowedOriginPatterns;

    public CorsConfig(org.springframework.core.env.Environment env) {
        String configuredOrigins = env.getProperty("app.cors.allowed-origins", DEFAULT_ALLOWED_ORIGINS);
        this.allowedOriginPatterns = Arrays.stream(configuredOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toArray(String[]::new);
        log.info("Configured CORS origins/patterns: {}", Arrays.toString(this.allowedOriginPatterns));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(allowedOriginPatterns)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
