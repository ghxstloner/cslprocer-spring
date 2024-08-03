package com.cslprocerbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para manejar CORS (Cross-Origin Resource Sharing) en la aplicación.
 */
@Configuration
public class CorsConfig {

    /**
     * Define un bean de configuración de MVC que personaliza el comportamiento de CORS.
     *
     * @return Un WebMvcConfigurer con la configuración CORS especificada.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configura las reglas de CORS para la aplicación.
             *
             * @param registry El registro de CORS que se utilizará para agregar las reglas de CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
