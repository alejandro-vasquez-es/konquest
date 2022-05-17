package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.interfaces.Validar;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.ValidacionesException;

import javax.swing.*;

public class FilaPlaneta implements Validar {

    protected int numeroFila;
    protected String nombre;
    protected int cantidadNaves;
    protected int producción;
    protected double porcentajeMuertes;
    protected Posicion posicion;
    protected JFrame parent;
    protected Mapa mapa;

    public FilaPlaneta(int numeroFila, String nombre, int cantidadNaves, int producción, double porcentajeMuertes, int columnaPosicion, int filaPosicion, JFrame parent, Mapa mapa) {
        this.numeroFila = numeroFila;
        this.nombre = nombre;
        this.cantidadNaves = cantidadNaves;
        this.producción = producción;
        this.porcentajeMuertes = porcentajeMuertes;
        this.posicion = new Posicion(columnaPosicion, filaPosicion);
        this.parent = parent;
        this.mapa = mapa;
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

    public void validar() throws ValidacionesException, ListaException {
        //entero entre 0 y 0.999999
        if (porcentajeMuertes < 0 || porcentajeMuertes > 0.999999) {
            throw new ValidacionesException("Error de la tabla de neutrales, en fila: " + numeroFila + "El porcentaje de muertes debe ser un entero entre 0 y 0.999999", parent);
        }

//        posicion fuera del tamaño del mapa
        if (posicion.esPosicionFueraIndice(mapa)) {
            throw new ValidacionesException("Error de la tabla de neutrales, en fila: " + numeroFila + ". Debe de tener una posición dentro del mapa", parent);
        }

        // Posicion ocupada
        if(mapa.esPosicionOcupada(posicion)){
            throw new ValidacionesException("Error de la tabla de neutrales, en fila: " + numeroFila + ". La posicion indicada ya está ocupada", parent);
        }

        //ningún valor puede ser negativo
        if(cantidadNaves < 0 || producción < 0){
            throw new ValidacionesException("Error de la tabla de neutrales, en fila: " + numeroFila + ". No se pueden usar número negativos", parent);
        }
    }
}
