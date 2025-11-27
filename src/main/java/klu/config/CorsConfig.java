package klu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // ✅ Allow all API endpoints
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:5173", // Vite dev server
                                "http://localhost:8080" ,
                                "http://localhost:3000"// Tomcat/Spring Boot
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
                
                // ✅ Allow static song files
                registry.addMapping("/songs/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET");

                // ✅ Allow static image files
                registry.addMapping("/images/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET");
            }
        };
    }
}
