package com.peruviana.enums;

public enum DocumentType{
    DNI("Dni"),
    RUC("Ruc"),
    PASSPORT("Pasaporte"),
    CE("Carnet de extranjería");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
