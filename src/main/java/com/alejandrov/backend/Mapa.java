package com.alejandrov.backend;

import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.planetas.Planeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Mapa implements Serializable {

    private String nombre;
    private int filas;
    private int columnas;
    private boolean alAzar;
    private boolean mapaCiego;
    private boolean acumulativo;
    private int turnosMaximos;
    private String tipo;
    private int planetasNeutrales;
    private Lista<Planeta> planetas;
    private boolean mostrarNavesNeutrales;
    private boolean mostrarEstadisticasPlanetasNeutrales;
    private int ProduccionPlanetasNeutrales;
    private Jugador[] jugadores;

    public Mapa(String nombre, int filas, int columnas, boolean alAzar, boolean mapaCiego, boolean acumulativo, int turnosMaximos, String tipo, int planetasNeutrales, boolean mostrarNavesNeutrales, boolean mostrarEstadisticasPlanetasNeutrales, int ProduccionPlanetasNeutrales) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.alAzar = alAzar;
        this.mapaCiego = mapaCiego;
        this.acumulativo = acumulativo;
        this.turnosMaximos = turnosMaximos;
        this.tipo = tipo;
        this.planetasNeutrales = planetasNeutrales;
        this.mostrarNavesNeutrales = mostrarNavesNeutrales;
        this.mostrarEstadisticasPlanetasNeutrales = mostrarEstadisticasPlanetasNeutrales;
        this.ProduccionPlanetasNeutrales = ProduccionPlanetasNeutrales;
        this.planetas = new Lista<Planeta>();
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public void guardarArchivo(String ruta) {

        File archivo = new File(ruta + ".mapk");

        try {
            FileOutputStream fos = new FileOutputStream(archivo, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar archivo");
        }

    }

    public String getNombre() {
        return nombre;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getTurnosMaximos() {
        return turnosMaximos;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPlanetasNeutrales() {
        return planetasNeutrales;
    }

    public Lista<Planeta> getPlanetas() {
        return planetas;
    }

    public int getProduccionPlanetasNeutrales() {
        return ProduccionPlanetasNeutrales;
    }

    public boolean esAlAzar() {
        return alAzar;
    }

    public boolean esMapaCiego() {
        return mapaCiego;
    }

    public boolean esAcumulativo() {
        return acumulativo;
    }

    public boolean seMuestranNavesNeutrales() {
        return mostrarNavesNeutrales;
    }

    public boolean seMuestranEstadisticasPlanetasNeutrales() {
        return mostrarEstadisticasPlanetasNeutrales;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void agregarPlaneta(Planeta planeta) {
        planetas.agregar(planeta);
    }

    public boolean esPosicionOcupada(Posicion posicion) throws ListaException {
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            Posicion posicionNodo = planetas.buscarIndice(i).getContenido().getPosicion();
            if (posicionNodo.esPosicionIgual(posicion)){
                return true;
            }
        }
        return false;
    }

    public void reiniciarListaPlanetas() {
        this.planetas = new Lista<Planeta>();
    }
}