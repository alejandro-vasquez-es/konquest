package com.alejandrov.backend;

import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;
import com.alejandrov.frontend.planetas.*;

public class Flota {

    private int numero;
    private int naves;
    private double porcentajeMuerte;
    private int turnoLlegada;
    private int turnoPartida;
    private Planeta origen;
    private Planeta destino;

    public Flota(int numero, int naves, double porcentajeMuerte, Planeta origen, Planeta destino, int turnoLlegada, int turnoPartida) {
        this.numero = numero;
        this.naves = naves;
        this.porcentajeMuerte = porcentajeMuerte;
        this.origen = origen;
        this.destino = destino;
        this.turnoLlegada = turnoLlegada;
        this.turnoPartida = turnoPartida;
    }

    public void aterrizar(Mapa mapa, KonquestFrame frame) throws ListaException {
        double porcentaje = Planeta.crearPorcentajeMuerteAleatorio();
        if (destino.isActivo()) {
            if (destino instanceof PlanetaFantasma) {
                ((PlanetaFantasma) destino).recibirIncursion(this, mapa, frame);
            } else if (destino instanceof PlanetaJugador && origen instanceof PlanetaJugador && ((PlanetaJugador) destino).getJugador().equals(((PlanetaJugador) origen).getJugador())) {
                aterrizarPlanetaAliado(frame);
            } else if (porcentaje > porcentajeMuerte && naves < destino.getCantidadNaves()) { // el planeta se defendió
                destino.agregarNaves(-naves);
                frame.agregarFlotaAterrizada("El planeta " + destino.getNombre() + " se defendió de un ataque del planeta " + origen.getNombre());
            } else if (origen instanceof PlanetaZombie) { // ataque zombie
                Cuadro cuadro = destino.getCuadro();
                if (destino instanceof PlanetaNeutral) {
                    mapa.getPlanetasNeutrales().eliminarContenido((PlanetaNeutral) destino); // eliminar el planeta de la lista de planetas neutrales del mapa
                } else if (destino instanceof PlanetaJugador) {
                    ((PlanetaJugador) destino).getJugador().getPlanetas().eliminarContenido((PlanetaJugador) destino); //eliminarle el planeta al jugador destino
                    mapa.getPlanetasJugador().eliminarContenido((PlanetaJugador) destino);// eliminar planeta del mapa
                }
                destino.setActivo(false);

                cuadro.setPlaneta(null);
                cuadro.removeAll();

                frame.agregarFlotaAterrizada("El planeta " + destino.getNombre() + " ha sido exterminado por el planeta zombie " + origen.getNombre());

            } else {// ataque exitoso
                if (destino instanceof PlanetaJugador) ((PlanetaJugador) destino).recibirIncursion(this, mapa, frame);
                if (destino instanceof PlanetaNeutral) ((PlanetaNeutral) destino).recibirIncursion(this, mapa, frame);

            }

        } else {
            origen.agregarNaves(naves);
        }
    }

    public void aterrizarPlanetaAliado(KonquestFrame frame) {
        destino.agregarNaves(naves);
        frame.agregarFlotaAterrizada("el jugador " + ((PlanetaJugador) origen).getJugador().getNombre() + " ha mandado " + naves + " naves al planeta " + destino.getNombre() + " desde el planeta " + origen.getNombre());
    }


    public void setNumero(int numero) {
        this.numero = numero;
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
