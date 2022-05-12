package com.alejandrov.backend;

public class Flota {

    private int numero;
//    private Planeta origen;
//    private Planeta destino;
    private int naves;
    private double porcentajeMuerte;
    private int turnoLlegada;

    public Flota(int numero, int naves, double porcentajeMuerte, int turnoLlegada) {
        this.numero = numero;
        this.naves = naves;
        this.porcentajeMuerte = porcentajeMuerte;
        this.turnoLlegada = turnoLlegada;
    }
}
