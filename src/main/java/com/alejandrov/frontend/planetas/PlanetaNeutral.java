package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Posicion;

import javax.swing.*;

public class PlanetaNeutral extends  Planeta{

    public PlanetaNeutral(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion) {
        super(nombre, cantidadNaves, posicion, porcentajeMuerte, produccion);
        imagen = new ImageIcon(getClass().getResource("/imagenes/planetas/NEUTRAL.png"));
    }

    @Override
    public void recibirIncursión() {

    }

}
