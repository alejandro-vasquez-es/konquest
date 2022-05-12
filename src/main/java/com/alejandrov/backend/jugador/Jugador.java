package com.alejandrov.backend.jugador;

public class Jugador {

    private String nombre;
    private String tipo;
    private String color;

    public Jugador(String nombre, String tipo, String color) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }
}
