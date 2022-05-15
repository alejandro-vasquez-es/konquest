package com.alejandrov.backend.enums;

public enum TiposMapa {
    VIA_LACTEA("via-lactea"),
    ANDROMEDA("andromeda"),
    CIRCINUS("circinus");

    final public String nombre;

    TiposMapa(String nombre) {
        this.nombre = nombre;
    }

}
