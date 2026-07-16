package com.peruviana.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePhoneDto {

    @NotBlank(message = "Ingrese su nuevo número de teléfono")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "^9\\d{8}$", message = "Ingrese un número de teléfono válido")
    private String phoneNumber;

    @NotBlank(message = "Ingrese su contraseña")
    private String password;
}