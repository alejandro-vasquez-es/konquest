package com.alejandrov.backend;

import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;
import com.alejandrov.frontend.planetas.Planeta;
import com.alejandrov.frontend.planetas.PlanetaFantasma;
import com.alejandrov.frontend.planetas.PlanetaJugador;
import com.alejandrov.frontend.planetas.PlanetaNeutral;

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
            if(destino instanceof PlanetaFantasma){
                destino.recibirIncursion(this, mapa, frame);
            } else if (destino instanceof PlanetaJugador && origen instanceof PlanetaJugador && ((PlanetaJugador) destino).getJugador().equals(((PlanetaJugador) origen).getJugador())) {
                aterrizarPlanetaAliado(frame);
            } else if (porcentaje > porcentajeMuerte) { // el planeta se defendió
                frame.agregarFlotaAterrizada("El planeta " + destino.getNombre() + " se defendió de un ataque del jugador " + ((PlanetaJugador) origen).getJugador().getNombre() + " lanzado desde el planeta " + origen.getNombre());
            } else { // ataque exitoso
                if (destino instanceof PlanetaJugador || destino instanceof PlanetaNeutral) {
                    destino.recibirIncursion(this, mapa, frame);
                }
            }

        }else {
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
