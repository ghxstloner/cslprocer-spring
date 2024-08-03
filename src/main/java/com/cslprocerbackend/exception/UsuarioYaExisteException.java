package com.cslprocerbackend.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta registrar un usuario que ya existe en el sistema.
 * Esta excepción extiende {@code RuntimeException} y se utiliza para señalar condiciones específicas en las que
 * la operación de registro de un usuario falla debido a que el usuario ya está presente en la base de datos.
 * La clase proporciona un constructor que acepta un mensaje de error, el cual se pasa al constructor de la superclase
 * {@code RuntimeException}. Este mensaje puede ser utilizado para proporcionar información adicional sobre la causa del error.
 *{@code public UsuarioYaExisteException(String message);} - Crea una nueva instancia de {@code UsuarioYaExisteException}
 *     con el mensaje de error especificado.
 * Esta excepción debe ser utilizada en el contexto de operaciones relacionadas con la gestión de usuarios, especialmente
 * en escenarios donde la integridad de los datos requiere evitar la duplicación de registros de usuario.
 */
public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
