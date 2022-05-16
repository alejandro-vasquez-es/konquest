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


    public String toString(boolean[] valores){
        /*mostrar naves = 0
        mostrar estadisticas = 1*/
        if(!valores[0] && valores[1])
            return  "nombre: " + nombre + ", porcentaje de muerte: " + porcentajeMuerte + ", producción: " + produccion;
        if(valores[0] && valores[1])
            return  "nombre: " + nombre + ", naves: " + cantidadNaves + ", porcentaje de muerte: " + porcentajeMuerte + ", producción: " + produccion;
        if(valores[0] && !valores[1])
            return  "nombre: " + nombre + ", naves: " + cantidadNaves;
        return "nombre: " + nombre;
    }

}
