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

    public void terminarTurno() {

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
        Lista<Flota> flotas = jugadorActivo.getFlotas();
        int numero = flotas.obtenerLongitud()+1;
        Planeta origen = cuadrosClickeados[0].getPlaneta();
        Planeta destino = cuadrosClickeados[1].getPlaneta();
        double porcentajeMuerte = origen.getPorcentajeMuerte();
        int turnoLlegada = Mapa.medirDistancia(origen.getPosicion(), destino.getPosicion());
        Flota nuevaFlota = new Flota(numero, naves, porcentajeMuerte,origen,destino, turnoLlegada, turno);
        origen.enviarNaves(naves);
        flotas.agregar(nuevaFlota);
    }


    public void setSiguienteJugadorActivo() {
        jugadorActivo = jugadores[indiceJugadorActivo];
        indiceJugadorActivo++;
        if (indiceJugadorActivo > jugadores.length) {
            indiceJugadorActivo = 0;
            turno++;
            frame.actualizarAreaMensajes();
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
}
