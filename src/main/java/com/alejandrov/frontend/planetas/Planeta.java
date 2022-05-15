package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;

import javax.swing.*;
import java.awt.*;

public abstract class Planeta {

    protected String nombre;
    protected int cantidadNaves;
    protected Posicion posicion;
    protected double porcentajeMuerte;
    protected int produccion;
    protected ImageIcon imagen;


    public Planeta(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion) {
        this.nombre = nombre;
        this.cantidadNaves = cantidadNaves;
        this.posicion = posicion;
        this.porcentajeMuerte = porcentajeMuerte;
        this.produccion = produccion;
    }

    public Planeta(){
        super();
    }



    public String getNombre() {
        return nombre;
    }

    public int getCantidadNaves() {
        return cantidadNaves;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public double getPorcentajeMuerte() {
        return porcentajeMuerte;
    }

    public int getProduccion() {
        return produccion;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public abstract void recibirIncursión();

    public static int crearCantidadDeNavesAleatoria() {
        return (int) Math.floor(Math.random() * (20 - 1 + 1) + 1);
    }

    public static int crearProduccionAleatoria() {
        return (int) Math.floor(Math.random() * (10 - 1 + 1) + 1);
    }

    public static double crearPorcentajeMuerteAleatorio() {
        return Math.floor(Math.random() * 999999) / 1000000;
    }

    public static Posicion crearPosiciónAleatoria(Mapa mapa) throws ListaException {
        int columna = (int) Math.floor(Math.random() * ((mapa.getColumnas() - 1) - 0 + 1) + 0);
        int fila = (int) Math.floor(Math.random() * ((mapa.getFilas() - 1) - 0 + 1) + 0);
        Posicion pos = new Posicion(columna, fila);
        if (mapa.esPosicionOcupada(pos)) {
            columna = (int) Math.floor(Math.random() * ((mapa.getColumnas() - 1) - 0 + 1) + 0);
            fila = (int) Math.floor(Math.random() * ((mapa.getFilas() - 1) - 0 + 1) + 0);
            pos = new Posicion(columna, fila);
        }

        return pos;
    }

}
