package com.alejandrov.backend;

import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.GanadorFrame;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;
import com.alejandrov.frontend.planetas.*;

import javax.swing.*;

public class MotorJuego {

    private final Lista<Jugador> jugadores;
    private final Mapa mapa;
    private KonquestFrame frame;
    private Jugador jugadorActivo;
    private int indiceJugadorActivo;
    private int turno;
    private int turnoZombie;
    private Lista<Flota> flotasZombies;

    public MotorJuego(Jugador[] jugadores, Mapa mapa) {
        this.jugadores = new Lista<Jugador>();
        for (int i = 0; i < jugadores.length; i++) {
            this.jugadores.agregar(jugadores[i]);
        }
        this.mapa = mapa;
    }

    public Lista<Jugador> getJugadores() {
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
        flotasZombies = new Lista<Flota>();
        turnoZombie = 1;
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

    public void aterrizarNaves() throws ListaException {
        for (int i = 0; i < jugadores.obtenerLongitud(); i++) {
            jugadores.obtenerContenido(i).aterrizarFlotas(turno, mapa, frame);
        }
        for (int i = 0; i < flotasZombies.obtenerLongitud(); i++) {
            Flota flota = flotasZombies.obtenerContenido(i);
            if (flota.getTurnoLlegada() == turno) {
                flota.aterrizar(mapa, frame);
                flotasZombies.eliminarElementoEnIndice(i);
                i--;
            }
        }
        for (int i = 0; i < flotasZombies.obtenerLongitud(); i++) {
            flotasZombies.obtenerContenido(i).setNumero(i + 1);
        }
    }

    public void setSiguienteJugadorActivo() throws ListaException {
        jugadorActivo = jugadores.obtenerContenido(indiceJugadorActivo);
        indiceJugadorActivo++;
        if (jugadorActivo == null) {
            try {
                frame.terminarTurnoJugador();
            } catch (ListaException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminarTurnoJugador() throws ListaException {
        if (indiceJugadorActivo == jugadores.obtenerLongitud()) {
            indiceJugadorActivo = 0;
            avanzarTurnoPartida();
        }
        setSiguienteJugadorActivo();

    }

    public void avanzarTurnoPartida() {
        try {
            turno++;
            producirPlanetas();
            aterrizarNaves();
            incursionesZombie();
            comprobarGanador();
        } catch (ListaException e) {
            e.printStackTrace();
        }
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

    public void comprobarGanador() throws ListaException {
        int jugadoresVivos = 0;
        int indiceGanador = 0;
        for (int i = 0; i < jugadores.obtenerLongitud(); i++) {
            if (jugadores.obtenerContenido(i).getPlanetas().obtenerLongitud() == 0 && jugadores.obtenerContenido(i).getFlotas().obtenerLongitud() == 0) {
                JOptionPane.showMessageDialog(frame, "El jugador " + jugadores.obtenerContenido(i).getNombre() + " ha perdido, sale de la partida.");
                jugadores.eliminarElementoEnIndice(i);
            } else {
                jugadoresVivos++;
                indiceGanador = i;
            }
        }
        if (jugadoresVivos == 1) {
            frame.limpiarFrame();
            GanadorFrame ganadorFrame = new GanadorFrame(jugadores.obtenerContenido(indiceGanador));
            ganadorFrame.setVisible(true);
            ganadorFrame.setLocationRelativeTo(frame);
        }
    }

    public void incursionesZombie() throws ListaException {
        if (turnoZombie != 2) {
            turnoZombie++;
        } else {
            for (int i = 0; i < mapa.getPlanetasZombie().obtenerLongitud(); i++) {
                PlanetaZombie zombie = mapa.getPlanetasZombie().obtenerContenido(i);
                zombie.realizarIncursion(mapa, flotasZombies, turno);
            }
            turnoZombie = 0;
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
