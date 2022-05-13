package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.ValidacionesException;

import javax.swing.*;

public class FilaPlanetaJugador {

    private int numeroFila;
    private String nombre;
    private int cantidadNaves;
    private int producción;
    private double porcentajeMuertes;
    private Posicion posicion;
    private String conquistador;
    private String tipo;
    private JFrame parent;
    private Mapa mapa;

    public FilaPlanetaJugador(int numeroFila, String nombre, int cantidadNaves, int producción, double porcentajeMuertes, int columnaPosicion, int filaPosicion, String conquistador, String tipo, JFrame parent, Mapa mapa) {
        this.numeroFila = numeroFila;
        this.nombre = nombre;
        this.cantidadNaves = cantidadNaves;
        this.producción = producción;
        this.porcentajeMuertes = porcentajeMuertes;
        this.posicion = new Posicion(columnaPosicion, filaPosicion);
        this.conquistador = conquistador;
        this.tipo = tipo;
        this.parent = parent;
        this.mapa = mapa;
    }

    public void validar() throws ValidacionesException, ListaException {
        //entero entre 0 y 0.999999
        if (porcentajeMuertes < 0 || porcentajeMuertes > 0.999999) {
            throw new ValidacionesException(obtenerMensajeValidacion(3) + "El porcentaje de muertes debe ser un entero entre 0 y 0.999999", parent);
        }

//        posicion fuera del tamaño del mapa
        if (posicion.esPosicionFueraIndice(mapa)) {
            throw new ValidacionesException("Error en fila: " + numeroFila + ". Debe de tener una posición dentro del mapa", parent);
        }

        // Posicion ocupada
        if(mapa.esPosicionOcupada(posicion)){
            throw new ValidacionesException("Error en fila: " + numeroFila + ". La posicion indicada ya está ocupada", parent);
        }

        //ningún valor puede ser negativo
        if(cantidadNaves < 0 || producción < 0){
            throw new ValidacionesException("Error en fila: " + numeroFila + ". No se pueden usar número negativos", parent);
        }
    }

    public String obtenerMensajeValidacion(int columna) {
        return "Error en fila: " + numeroFila + ", columna: " + columna + ". ";
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadNaves() {
        return cantidadNaves;
    }

    public int getProducción() {
        return producción;
    }

    public double getPorcentajeMuertes() {
        return porcentajeMuertes;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public String getConquistador() {
        return conquistador;
    }

    public String getTipo() {
        return tipo;
    }
}
