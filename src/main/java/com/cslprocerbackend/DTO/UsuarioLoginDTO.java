package com.cslprocerbackend.DTO;

import lombok.Data;

/**
 * DTO para la información de inicio de sesión del usuario.
 * Esta clase encapsula los datos necesarios para que un usuario inicie sesión,
 * incluyendo el correo electrónico y la contraseña. Utiliza la anotación
 * Data de Lombok para generar automáticamente los métodos getters, setters, toString, equals y hashCode.
 */
@Data
public class UsuarioLoginDTO {

    /**
     * El correo electrónico del usuario.
     */
    private String email;

    /**
     * La contraseña del usuario.
     */
    private String password;
}


