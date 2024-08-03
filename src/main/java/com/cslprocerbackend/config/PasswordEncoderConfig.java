package com.cslprocerbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para gestionar el cifrado de contraseñas en la aplicación.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Define un bean que proporciona una instancia de PasswordEncoder.
     *
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
