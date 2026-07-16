package com.peruviana.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeEmailDto {

    @NotBlank(message = "Ingrese su nuevo correo")
    @Email(message = "Ingrese un correo válido")
    private String email;

    @NotBlank(message = "Ingrese su contraseña")
    private String password;
}