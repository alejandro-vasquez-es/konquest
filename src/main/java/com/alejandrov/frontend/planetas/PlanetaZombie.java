package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;

import javax.swing.*;
import java.util.Objects;

public class PlanetaZombie extends Planeta {

    public PlanetaZombie(String nombre, int cantidadNaves, Mapa mapa) throws ListaException {
        super(nombre, cantidadNaves, crearPosiciónAleatoria(mapa), crearPorcentajeMuerteAleatorio(), crearProduccionAleatoria());
        imagen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/imagenes/planetas/ZOMBIE.png")));
        //TODO
    }

    @Override
    public void producirNaves(boolean esAcumulable) {

    }

    public void realizarIncursion(Mapa mapa, Lista<Flota> flotasZombie, int turno) throws ListaException {
        Cuadro[][] cuadros = mapa.getCuadros();

        int columna = (int) Math.floor(Math.random() * ((mapa.getColumnas() - 1) - 0 + 1) + 0);
        int fila = (int) Math.floor(Math.random() * ((mapa.getFilas() - 1) - 0 + 1) + 0);
        Posicion posicionAleatoria = new Posicion(columna, fila);
        while (!mapa.esPosicionOcupada(posicionAleatoria) || cuadros[columna][fila].getPlaneta() instanceof PlanetaZombie) {
            columna = (int) Math.floor(Math.random() * ((mapa.getColumnas() - 1) - 0 + 1) + 0);
            fila = (int) Math.floor(Math.random() * ((mapa.getFilas() - 1) - 0 + 1) + 0);
            posicionAleatoria = new Posicion(columna, fila);
        }

        Cuadro cuadro = cuadros[columna][fila];
        Planeta destino = cuadro.getPlaneta();
        if (destino != null && destino.isActivo()) {
            int turnoLlegada = Mapa.medirDistancia(posicion, destino.getPosicion()) + turno;
            Flota flota = new Flota(flotasZombie.obtenerLongitud() + 1, cantidadNaves, porcentajeMuerte, this, destino, turnoLlegada, turno);
            flotasZombie.agregar(flota);
        }

    }
}
