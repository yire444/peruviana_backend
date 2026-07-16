package com.peruviana.user.dto;

import com.peruviana.enums.DocumentType;
import com.peruviana.enums.Role;

public record UserResponseDto(

        Long id,
        String email,
        String name,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String phoneNumber,
        Role role,
        Boolean active
) { }
