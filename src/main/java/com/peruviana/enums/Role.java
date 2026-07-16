package com.peruviana.enums;

public enum Role {
    CUSTOMER("Cliente"),
    ADMIN("Administrador");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
