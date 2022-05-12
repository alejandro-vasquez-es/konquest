package com.alejandrov.backend;

public class Mapa {

    private String name;
    private int filas;
    private int columnas;
    private boolean generarAlAzar;
    private boolean esMapaCiego;
    private boolean esAcumulativo;
    private int turnosMaximos;
    private String tipo;
    private int planetasNeutrales;
//    private Planeta[] planetas;
    private boolean mostrarNavesNeutrales;
    private boolean mostrarEstadisticasPlanetasNeutrales;
    private int ProduccionPlanetasNeutrales;

    public Mapa(String name, int filas, int columnas, boolean generarAlAzar, boolean esMapaCiego, boolean esAcumulativo, int turnosMaximos, String tipo, int planetasNeutrales, boolean mostrarNavesNeutrales, boolean mostrarEstadisticasPlanetasNeutrales, int ProduccionPlanetasNeutrales){
        this.name = name;
        this.filas = filas;
        this.columnas = columnas;
        this.generarAlAzar = generarAlAzar;
        this.esMapaCiego = esMapaCiego;
        this.esAcumulativo = esAcumulativo;
        this.turnosMaximos = turnosMaximos;
        this.tipo = tipo;
        this.planetasNeutrales = planetasNeutrales;
        this.mostrarNavesNeutrales = mostrarNavesNeutrales;
        this.mostrarEstadisticasPlanetasNeutrales = mostrarEstadisticasPlanetasNeutrales;
        this.ProduccionPlanetasNeutrales = ProduccionPlanetasNeutrales;
    }

}
