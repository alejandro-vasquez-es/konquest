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

    public void setFila(int fila) {
        this.fila = fila;
    }
}
