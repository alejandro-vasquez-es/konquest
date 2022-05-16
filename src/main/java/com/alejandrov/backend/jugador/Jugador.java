package com.alejandrov.backend.jugador;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.frontend.planetas.PlanetaJugador;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private String tipo;
    private String color;
    private Lista<PlanetaJugador> planetas;
    private Lista<Flota> flotas;


    public Jugador(String nombre, String tipo, String color) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.color = color;
        planetas = new Lista<PlanetaJugador>();
        flotas = new Lista<Flota>();
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

    public Lista<Flota> getFlotas() {
        return flotas;
    }
}
