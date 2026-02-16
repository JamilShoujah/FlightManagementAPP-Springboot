// src/main/java/com/yourapp/config/CorsConfig.java
package jamil.FlightFoodService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private final Environment env;

    public CorsConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                String[] allowedOrigins;

                // Set allowed origins based on active profile
                String activeProfile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "dev";

                switch (activeProfile) {
                    case "prod":
                        allowedOrigins = new String[] { "https://myapp.com" }; // prod frontend
                        break;
                    case "staging":
                        allowedOrigins = new String[] { "https://staging.myapp.com" };
                        break;
                    default: // dev
                        allowedOrigins = new String[] { "http://localhost:4200" };
                }

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
