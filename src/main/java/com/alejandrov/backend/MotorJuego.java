package com.alejandrov.backend;

import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;

public class MotorJuego {

    private final Jugador[] jugadores;
    private final Mapa mapa;
    private Lista<Flota> flotas;

    public MotorJuego(Jugador[] jugadores, Mapa mapa) {
        this.jugadores = jugadores;
        this.mapa = mapa;
        flotas = new Lista<Flota>();
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre() == nombre) {
                return jugador;
            }
        }
        return null;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }
}
