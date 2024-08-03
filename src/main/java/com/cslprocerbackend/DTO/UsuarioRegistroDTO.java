package com.cslprocerbackend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Clase de Data Transfer Object (DTO) que representa la información necesaria para el registro de un usuario.
 * El uso de esta clase permite transferir datos entre capas de la aplicación o entre servicios de manera estructurada
 * y organizada. Los DTOs son útiles para mantener una separación clara entre la capa de presentación y la capa de lógica de negocio.
 * La anotación {@code @Data} de Lombok genera automáticamente getters, setters, {@code toString()}, {@code equals()}
 * y {@code hashCode()} para los campos de la clase.
 * La anotación {@code @NoArgsConstructor} proporciona un constructor sin argumentos, necesario para algunos frameworks
 * y librerías que requieren un constructor vacío.
 * La anotación {@code @AllArgsConstructor} genera un constructor con todos los parámetros, lo que facilita la creación
 * de instancias de la clase con todos los valores necesarios.
 *  {@code private String nombre;} - El nombre del usuario.
 *  {@code private String email;} - La dirección de correo electrónico del usuario.
 *  {@code private String telefono;} - El número de teléfono del usuario.
 *  {@code private String password;} - La contraseña del usuario.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String password;
}
