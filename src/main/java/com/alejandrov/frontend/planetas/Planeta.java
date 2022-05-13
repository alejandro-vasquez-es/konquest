package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Posicion;

import javax.swing.*;

public class Planeta extends JLabel {

    protected String nombre;
    protected int cantidadNaves;
    protected Posicion posicion;
    protected double porcentajeMuerte;
    protected int produccion;
    protected ImageIcon imagen;


    public Planeta(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion) {
        super(nombre, null, JLabel.CENTER);
        this.nombre = nombre;
        this.cantidadNaves = cantidadNaves;
        this.posicion = posicion;
        this.porcentajeMuerte = porcentajeMuerte;
        this.produccion = produccion;
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
}
