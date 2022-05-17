package com.alejandrov.backend.jugador;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.planetas.Planeta;
import com.alejandrov.frontend.planetas.PlanetaJugador;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private String tipo;
    private String color;
    private Lista<PlanetaJugador> planetas;
    private Lista<Flota> flotas;


    public Jugador(String nombre, String tipo, String color) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.color = color;
        planetas = new Lista<PlanetaJugador>();
        flotas = new Lista<Flota>();
    }

    public Flota agregarFlota(Planeta origen, Planeta destino, int naves, int turno) {
        int numero = flotas.obtenerLongitud() + 1;
        double porcentajeMuerte = origen.getPorcentajeMuerte();
        int turnoLlegada = Mapa.medirDistancia(origen.getPosicion(), destino.getPosicion());
        Flota nuevaFlota = new Flota(numero, naves, porcentajeMuerte, origen, destino, (turnoLlegada + turno), turno);
        origen.enviarNaves(naves);
        flotas.agregar(nuevaFlota);
        return nuevaFlota;
    }

    public void cancelarFlota(Flota flota) throws ListaException {
        flota.getOrigen().agregarNaves(flota.getNaves());
        flotas.eliminarElementoEnIndice(flota.getNumero() - 1);
        for (int i = 0; i < flotas.obtenerLongitud(); i++) {
            flotas.obtenerContenido(i).setNumero(i + 1);
        }
    }

    public void aterrizarFlotas(int turno, Mapa mapa, KonquestFrame frame) throws ListaException {
        for (int i = 0; i < flotas.obtenerLongitud(); i++) {
            Flota flota = flotas.obtenerContenido(i);
            if(flota.getTurnoLlegada() == turno){
                flota.aterrizar(mapa, frame);
                flotas.eliminarElementoEnIndice(i);
                i--;
            }
        }
        for (int i = 0; i < flotas.obtenerLongitud(); i++) {
            flotas.obtenerContenido(i).setNumero(i + 1);
        }
    }

    public void ganar() {

    }

    public Lista<PlanetaJugador> getPlanetas() {
        return planetas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public Lista<Flota> getFlotas() {
        return flotas;
    }
}
