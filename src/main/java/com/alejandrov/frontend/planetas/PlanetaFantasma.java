package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;

import javax.swing.*;

public class PlanetaFantasma extends Planeta{

    public PlanetaFantasma(String nombre, Mapa mapa) throws ListaException {
        super(nombre + "fantasma", crearCantidadDeNavesAleatoria(), crearPosiciónAleatoria(mapa), crearPorcentajeMuerteAleatorio(), crearProduccionAleatoria());
        imagen = new ImageIcon(getClass().getResource("/imagenes/planetas/NEUTRAL.png"));
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


    @Override
    public void recibirIncursion(Flota flota, Mapa mapa, KonquestFrame frame) throws ListaException {

        PlanetaJugador origen = (PlanetaJugador) flota.getOrigen();
        Cuadro cuadro = getCuadro();

        activo = false;
        mapa.getPlanetasFantasma().eliminarContenido(this); // eliminar el planeta de la lista de planetas neutrales del mapa

        cuadro.setPlaneta(null);
        cuadro.removeAll();

        frame.agregarFlotaAterrizada("El planeta " + nombre + " era un planeta fantasma por lo que la incursión hecha por el jugador " + origen.getJugador().getNombre() + " realizada desde el planeta " + origen.getNombre() + " se predió en el espacio :(");

    }
}
