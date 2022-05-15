package com.alejandrov.backend.jugador;

import com.alejandrov.backend.listas.Lista;
import com.alejandrov.frontend.planetas.PlanetaJugador;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private String tipo;
    private String color;
    private Lista<PlanetaJugador> planetas;


    public Jugador(String nombre, String tipo, String color) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.color = color;
        planetas = new Lista<PlanetaJugador>();
    }

    public Lista<PlanetaJugador> getPlanetas() {
        return planetas;
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
