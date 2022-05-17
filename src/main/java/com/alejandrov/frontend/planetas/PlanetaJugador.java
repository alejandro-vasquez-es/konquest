package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;

import javax.swing.*;

public class PlanetaJugador extends Planeta implements Terraformable {

    private Jugador jugador;
    private String tipo;

    public PlanetaJugador(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion, Jugador jugador, String tipo) {
        super(nombre, cantidadNaves, posicion, porcentajeMuerte, produccion);
        this.jugador = jugador;
        this.tipo = tipo;
        this.imagen = new ImageIcon(getClass().getResource("/imagenes/planetas/" + tipo.toUpperCase() + ".png"));
    }

    @Override
    public void recibirIncursion(Flota flota, Mapa mapa, KonquestFrame frame) throws ListaException {
        PlanetaJugador origen = (PlanetaJugador) flota.getOrigen();
        PlanetaJugador planeta = terraformar(flota);
        Cuadro cuadro = getCuadro();
        jugador.getPlanetas().eliminarContenido(this); //eliminarle el planeta al jugador destino
        jugador.getPlanetas().agregar(planeta);// agregarle el planeta al jugador origen
        activo = false;

        mapa.getPlanetasJugador().cambiarContenido(this, planeta);
        cuadro.setPlaneta(planeta);
        cuadro.actualizar();

        frame.agregarFlotaAterrizada("El planeta " + origen.getNombre() + " del jugador " + origen.getJugador().getNombre() + " ha conquistado al planeta " + nombre + " del jugador " + jugador.getNombre());

    }

    public String toString(boolean esCiego) {
        if (!esCiego)
            return "nombre: " + nombre + ", naves: " + cantidadNaves + ", porcentaje de muerte: " + porcentajeMuerte + ", producción: " + produccion + ", jugador: " + jugador.getNombre();
        return "nombre: " + nombre + ", jugador: " + jugador.getNombre();
    }

    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public PlanetaJugador terraformar(Flota flota/*, MotorJuego juego*/) {
        Jugador jugador = ((PlanetaJugador) flota.getOrigen()).getJugador();
        PlanetaJugador planeta = new PlanetaJugador(nombre,flota.getNaves() + cantidadNaves,posicion,porcentajeMuerte,produccion,jugador,jugador.getColor());
        planeta.imagen = flota.getOrigen().getImagen();
        return planeta;
    }
}
