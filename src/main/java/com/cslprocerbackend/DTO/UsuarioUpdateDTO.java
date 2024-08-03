package com.cslprocerbackend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioUpdateDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private boolean cambiarPassword;
    private List<String> roles; // Lista de nombres de roles asignados
}
