package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;

import javax.swing.*;

public abstract class Planeta {

    protected String nombre;
    protected int cantidadNaves;
    protected Posicion posicion;
    protected double porcentajeMuerte;
    protected int produccion;
    protected ImageIcon imagen;
    protected Cuadro cuadro;
    protected boolean activo;

    public Planeta(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion) {
        this.nombre = nombre;
        this.cantidadNaves = cantidadNaves;
        this.posicion = posicion;
        this.porcentajeMuerte = porcentajeMuerte;
        this.produccion = produccion;
        activo = true;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
    }

    public Cuadro getCuadro() {
        return cuadro;
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

    public void enviarNaves(int navesEnviadas) {
        this.cantidadNaves -= navesEnviadas;
    }

    public void agregarNaves(int navesRegresadas) {
        cantidadNaves += navesRegresadas;
    }

    public void producirNaves(boolean esAcumulable){
        if(esAcumulable) produccion ++;
        cantidadNaves += produccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public abstract void recibirIncursion(Flota flota, Mapa mapa, KonquestFrame frame) throws ListaException;
}
