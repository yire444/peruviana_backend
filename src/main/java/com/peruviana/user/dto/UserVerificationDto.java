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
public class UserVerificationDto {

    @NotBlank(message = "El código de verificación es obligatorio")
    @Size(min = 6, max = 6, message = "Código inválido")
    @Pattern(regexp = "^\\d+$", message = "Código inválido")
    private String code;
}