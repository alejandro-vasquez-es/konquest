package com.alejandrov.backend;

import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.planetas.Planeta;

public class Flota {

    private int numero;
    private int naves;
    private double porcentajeMuerte;
    private int turnoLlegada;
    private int turnoPartida;
    private Planeta origen;
    private Planeta destino;

    public Flota(int numero, int naves, double porcentajeMuerte,Planeta origen, Planeta destino, int turnoLlegada, int turnoPartida) {
        this.numero = numero;
        this.naves = naves;
        this.porcentajeMuerte = porcentajeMuerte;
        this.origen = origen;
        this.destino = destino;
        this.turnoLlegada = turnoLlegada;
        this.turnoPartida = turnoPartida;
    }

    public void decrecerNúmero() {
        numero -= 1;
    }

    public void cancelarFlota(Lista<Flota> flotas) throws ListaException {
        origen.regresarNaves(naves);
        int indiceAnterior = flotas.obtenerIndice(this);
        flotas.eliminarContenido(this);
        for (int i = indiceAnterior; i < flotas.obtenerLongitud(); i++) {
            flotas.obtenerContenido(i).decrecerNúmero();
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getNaves() {
        return naves;
    }

    public double getPorcentajeMuerte() {
        return porcentajeMuerte;
    }

    public int getTurnoLlegada() {
        return turnoLlegada;
    }

    public int getTurnoPartida() {
        return turnoPartida;
    }

    public Planeta getOrigen() {
        return origen;
    }

    public Planeta getDestino() {
        return destino;
    }
}
