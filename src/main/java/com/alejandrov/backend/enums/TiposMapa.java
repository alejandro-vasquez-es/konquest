package com.alejandrov.backend.enums;

public enum TiposMapa {
    VIA_LACTEA("Vía lactea"),
    ANDROMEDA("Andrómeda"),
    CIRCINUS("Circinus");

    final public String nombre;

    TiposMapa(String nombre) {
        this.nombre = nombre;
    }

}
