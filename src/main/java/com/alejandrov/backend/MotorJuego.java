package com.alejandrov.backend;

import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;
import com.alejandrov.frontend.planetas.*;

import javax.swing.*;

public class MotorJuego {

    private final Jugador[] jugadores;
    private final Mapa mapa;
    private Lista<Flota> flotas;
    private KonquestFrame frame;
    private Jugador jugadorActivo;
    private int indiceJugadorActivo;
    private int turno;

    public MotorJuego(Jugador[] jugadores, Mapa mapa) {
        this.jugadores = jugadores;
        this.mapa = mapa;
        flotas = new Lista<Flota>();
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Lista<Flota> getFlotas() {
        return flotas;
    }

    public void empezarPartida(KonquestFrame frame) throws ListaException {
        this.frame = frame;
        JOptionPane.showMessageDialog(frame, "La partida a empezado", "Parida empezada", JOptionPane.INFORMATION_MESSAGE);
        prepararFrame();
        cargarPlanetasNeutrales();
        cargarPlanetasJugador();
        cargarPlanetasZombie();
        cargarPlanetasFantasma();
        this.indiceJugadorActivo = 0;
        this.turno = 0;
        setSiguienteJugadorActivo();
    }

    public void cargarPlanetasNeutrales() throws ListaException {
        for (int i = 0; i < mapa.getPlanetasNeutrales().obtenerLongitud(); i++) {
            PlanetaNeutral planeta = mapa.getPlanetasNeutrales().obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            int columna = pos.getColumna();
            int fila = pos.getFila();
            Cuadro planetaCuadro = frame.getCuadros()[columna][fila];
            planetaCuadro.setPlaneta(planeta);
            planetaCuadro.setIcon();
        }
    }

    public void cargarPlanetasJugador() throws ListaException {
        for (int i = 0; i < mapa.getPlanetasJugador().obtenerLongitud(); i++) {
            PlanetaJugador planeta = mapa.getPlanetasJugador().obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            int columna = pos.getColumna();
            int fila = pos.getFila();
            Cuadro planetaCuadro = frame.getCuadros()[columna][fila];
            planetaCuadro.setPlaneta(planeta);
            planetaCuadro.setIcon();
        }
    }

    public void cargarPlanetasZombie() throws ListaException {
        for (int i = 0; i < mapa.getPlanetasZombie().obtenerLongitud(); i++) {
            PlanetaZombie planeta = mapa.getPlanetasZombie().obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            int columna = pos.getColumna();
            int fila = pos.getFila();
            Cuadro planetaCuadro = frame.getCuadros()[columna][fila];
            planetaCuadro.setPlaneta(planeta);
            planetaCuadro.setIcon();
        }
    }

    public void cargarPlanetasFantasma() throws ListaException {
        for (int i = 0; i < mapa.getPlanetasFantasma().obtenerLongitud(); i++) {
            PlanetaFantasma planeta = mapa.getPlanetasFantasma().obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            int columna = pos.getColumna();
            int fila = pos.getFila();
            Cuadro planetaCuadro = frame.getCuadros()[columna][fila];
            planetaCuadro.setPlaneta(planeta);
            planetaCuadro.setIcon();
        }
    }

    public void prepararFrame() {
        frame.getMessages().setVisible(true);
        frame.getCenter().removeAll();
        frame.getCenter().revalidate();
        frame.getCenter().repaint();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/mapa/" + mapa.getTipo() + ".jpg"));
        frame.setFondo(imagen);
        frame.crearCuadricula(mapa);
    }

    public void setSiguienteJugadorActivo() {
        jugadorActivo = jugadores[indiceJugadorActivo];
        indiceJugadorActivo++;
        if (indiceJugadorActivo > jugadores.length) {
            indiceJugadorActivo = 0;
            turno++;
        }
    }

}
