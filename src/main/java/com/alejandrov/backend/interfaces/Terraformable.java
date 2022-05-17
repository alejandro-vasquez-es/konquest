package com.alejandrov.backend.interfaces;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.MotorJuego;
import com.alejandrov.frontend.planetas.PlanetaJugador;

public interface Terraformable {
    public abstract PlanetaJugador terraformar(Flota flota/*, MotorJuego juego*/);
}
