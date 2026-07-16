package com.peruviana.user.dto;

import com.peruviana.enums.DocumentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotNull(message = "Seleccione su tipo de documento")
    private DocumentType documentType;

    @NotBlank(message = "Ingrese su número de documento")
    @Size(min = 8, max = 12)
    @Pattern(regexp = "^\\d+$", message = "Ingrese un número de documento válido")
    private String documentNumber;

    @NotBlank(message = "Ingrese su nombre")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "El nombre solo debe contener letras")
    private String name;

    @NotBlank(message = "Ingrese su apellido")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "El apellido solo debe contener letras")
    private String lastName;

    @NotBlank(message = "Ingrese su correo")
    @Email(message = "Ingrese un correo válido")
    private String email;

    @NotBlank(message = "Ingrese su número de teléfono")
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "^9\\d{8}$", message = "Ingrese un número de teléfono válido")
    private String phoneNumber;

    @NotBlank(message = "Ingrese su contraseña")
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String password;

    @NotBlank(message = "Confirme su contraseña")
    private String confirmPassword;
}