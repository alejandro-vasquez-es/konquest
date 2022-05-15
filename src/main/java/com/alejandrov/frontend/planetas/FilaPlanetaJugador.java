package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.ValidacionesException;

import javax.swing.*;

public class FilaPlanetaJugador extends FilaPlaneta {

    private String conquistador;
    private String tipo;

    public FilaPlanetaJugador(int numeroFila, String nombre, int cantidadNaves, int producción, double porcentajeMuertes, int columnaPosicion, int filaPosicion, JFrame parent, Mapa mapa, String conquistador, String tipo) {
        super(numeroFila, nombre, cantidadNaves, producción, porcentajeMuertes, columnaPosicion, filaPosicion, parent, mapa);
        this.conquistador = conquistador;
        this.tipo = tipo;
    }

    @Override
    public void validar() throws ValidacionesException, ListaException {
        //entero entre 0 y 0.999999
        if (porcentajeMuertes < 0 || porcentajeMuertes > 0.999999) {
            throw new ValidacionesException("Error de la tabla de jugadores, en la fila"+ numeroFila + "El porcentaje de muertes debe ser un entero entre 0 y 0.999999", parent);
        }

//        posicion fuera del tamaño del mapa
        if (posicion.esPosicionFueraIndice(mapa)) {
            throw new ValidacionesException("Error de la tabla de jugadores, en fila: " + numeroFila + ". Debe de tener una posición dentro del mapa", parent);
        }

        // Posicion ocupada
        if(mapa.esPosicionOcupada(posicion)){
            throw new ValidacionesException("Error de la tabla de jugadores, en fila: " + numeroFila + ". La posicion indicada ya está ocupada", parent);
        }

        //ningún valor puede ser negativo
        if(cantidadNaves < 0 || producción < 0){
            throw new ValidacionesException("Error de la tabla de jugadores, en fila: " + numeroFila + ". No se pueden usar número negativos", parent);
        }
    }

    public String getConquistador() {
        return conquistador;
    }

    public String getTipo() {
        return tipo;
    }
}
