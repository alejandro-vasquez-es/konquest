package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;

import javax.swing.*;

public class PlanetaFantasma extends Planeta{

    public PlanetaFantasma(String nombre, Mapa mapa) throws ListaException {
        super(nombre, crearCantidadDeNavesAleatoria(), crearPosiciónAleatoria(mapa), crearPorcentajeMuerteAleatorio(), crearProduccionAleatoria());
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
