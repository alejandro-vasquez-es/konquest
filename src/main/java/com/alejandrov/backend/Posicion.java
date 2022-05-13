package com.alejandrov.backend;

public class Posicion {

    private int columna;
    private int fila;

    public Posicion(int columna, int fila) {
        this.columna = columna;
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }

    public boolean esPosicionFueraIndice(Mapa mapa) {
        return (mapa.getColumnas() < columna || mapa.getFilas() < fila || 0 > columna || 0 > fila);
    }

    public boolean esPosicionIgual(Posicion posicion) {
        return (columna == posicion.getColumna() && fila == posicion.getFila());
    }



}
