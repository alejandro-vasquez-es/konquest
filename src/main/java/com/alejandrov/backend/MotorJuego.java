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
    private KonquestFrame frame;
    private Jugador jugadorActivo;
    private int indiceJugadorActivo;
    private int turno;

    public MotorJuego(Jugador[] jugadores, Mapa mapa) {
        this.jugadores = jugadores;
        this.mapa = mapa;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void empezarPartida(KonquestFrame frame) throws ListaException {
        this.frame = frame;
        this.indiceJugadorActivo = 0;
        this.turno = 1;
        setSiguienteJugadorActivo();
        frame.prepararFrame(this);
        cargarPlanetas(mapa.getPlanetasNeutrales());
        cargarPlanetas(mapa.getPlanetasJugador());
        cargarPlanetas(mapa.getPlanetasZombie());
        cargarPlanetas(mapa.getPlanetasFantasma());
        JOptionPane.showMessageDialog(frame, "La partida ha empezado", "Partida empezada", JOptionPane.INFORMATION_MESSAGE);
        frame.revalidate();
        frame.repaint();
    }

    public void cargarPlanetas(Lista planetas) throws ListaException {
        int longitud = planetas.obtenerLongitud();
        for (int i = 0; i < longitud; i++) {
            Planeta planeta = (Planeta) planetas.obtenerContenido(i);
            Posicion pos = planeta.getPosicion();
            int columna = pos.getColumna();
            int fila = pos.getFila();
            Cuadro planetaCuadro = frame.getCuadros()[columna][fila];
            planetaCuadro.setPlaneta(planeta);
            planetaCuadro.setIcon();
        }
    }

    public void nuevaFlota(Cuadro[] cuadrosClickeados, int naves) {
        Planeta origen = cuadrosClickeados[0].getPlaneta();
        Planeta destino = cuadrosClickeados[1].getPlaneta();
        jugadorActivo.agregarFlota(origen, destino, naves, turno);
    }

//    public void cargarFlotas() throws ListaException {
//        for (int i = 0; i < jugadores.length; i++) {
//            for (int j = 0; j < jugadores[i].getFlotas().obtenerLongitud(); j++) {
//                Flota flota = jugadores[i].getFlotas().obtenerContenido(j);
//                if (!flotas.incluye(flota)) {
//                    flotas.agregar(flota);
//                    flotasEnTurno.agregar(flota);
//                }
//            }
//        }
//    }

    public void aterrizarNaves() throws ListaException {
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i].aterrizarFlotas(turno, mapa, frame);
        }
    }


    public void setSiguienteJugadorActivo() {
        jugadorActivo = jugadores[indiceJugadorActivo];
        indiceJugadorActivo++;
    }

    public void terminarTurnoJugador() {
        if (indiceJugadorActivo == jugadores.length)
            avanzarTurnoPartida();
        setSiguienteJugadorActivo();

    }

    public void producirPlanetas() {
        Lista<Planeta> planetas = mapa.getPlanetas();
        for (int i = 0; i < planetas.obtenerLongitud(); i++) {
            try {
                planetas.obtenerContenido(i).producirNaves(mapa.esAcumulativo());
            } catch (ListaException e) {
                e.printStackTrace();
            }
        }
    }

    public void avanzarTurnoPartida() {
        try {
            indiceJugadorActivo = 0;
            turno++;
            producirPlanetas();
            aterrizarNaves();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public int getTurno() {
        return turno;
    }

    public KonquestFrame getFrame() {
        return frame;
    }

    public int getIndiceJugadorActivo() {
        return indiceJugadorActivo;
    }


}
