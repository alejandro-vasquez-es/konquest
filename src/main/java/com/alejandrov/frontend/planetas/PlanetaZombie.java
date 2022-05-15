package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;

import javax.swing.*;
import java.util.Objects;

public class PlanetaZombie extends Planeta{

    public PlanetaZombie(String nombre, int cantidadNaves, Mapa mapa) throws ListaException {
        super(nombre, cantidadNaves, crearPosiciónAleatoria(mapa), crearPorcentajeMuerteAleatorio(), crearProduccionAleatoria());
        imagen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/imagenes/planetas/ZOMBIE.png")));
        //TODO
    }

    @Override
    public void recibirIncursión() {

    }
}
