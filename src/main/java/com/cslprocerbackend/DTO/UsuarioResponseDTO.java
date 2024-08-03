package com.cslprocerbackend.DTO;

import lombok.Data;

/**
 * Clase de Data Transfer Object (DTO) que representa la información de respuesta del usuario.
 * Esta clase se utiliza para transferir los datos del usuario que se deben mostrar o devolver en las respuestas
 * a las solicitudes de los clientes. A diferencia de los DTOs de entrada, los DTOs de respuesta están diseñados para
 * proporcionar información que se ha procesado o recuperado de la base de datos.
 * La clase incluye un constructor por defecto sin argumentos proporcionado explícitamente, lo que es necesario
 * para algunos frameworks y librerías que requieren un constructor vacío para la deserialización o la creación de
 * instancias.
 */

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    public UsuarioResponseDTO() {
    }
}
