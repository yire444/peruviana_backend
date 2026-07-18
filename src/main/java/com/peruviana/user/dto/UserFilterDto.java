package com.peruviana.user.dto;

import lombok.Data;

@Data
public class UserFilterDto {

    private String documentNumber;
    private String email;
    private String phoneNumber;
    private Boolean active;
}
