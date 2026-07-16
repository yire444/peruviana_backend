package com.peruviana.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordDto {

    @NotBlank(message = "Debe ingresar su contraseña actual")
    private String currentPassword;

    @NotBlank(message = "Debe ingresar su nueva contraseña")
    @Size(min = 8, max = 50, message = "La nueva contraseña debe tener entre 8 y 50 caracteres")
    private String newPassword;

    @NotBlank(message = "Confirme su nueva contraseña")
    private String confirmNewPassword;
}
