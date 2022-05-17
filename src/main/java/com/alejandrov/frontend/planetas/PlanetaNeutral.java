package com.alejandrov.frontend.planetas;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.interfaces.RecibirIncursion;
import com.alejandrov.backend.interfaces.Terraformable;
import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;
import com.alejandrov.frontend.componentes.Cuadro;

import javax.swing.*;

public class PlanetaNeutral extends Planeta implements Terraformable, RecibirIncursion {

    public PlanetaNeutral(String nombre, int cantidadNaves, Posicion posicion, double porcentajeMuerte, int produccion) {
        super(nombre, cantidadNaves, posicion, porcentajeMuerte, produccion);
        imagen = new ImageIcon(getClass().getResource("/imagenes/planetas/NEUTRAL.png"));
    }


    public String toString(boolean[] valores) {
        /*mostrar naves = 0
        mostrar estadisticas = 1*/
        if (!valores[0] && valores[1])
            return "nombre: " + nombre + ", porcentaje de muerte: " + porcentajeMuerte + ", producción: " + produccion;
        if (valores[0] && valores[1])
            return "nombre: " + nombre + ", naves: " + cantidadNaves + ", porcentaje de muerte: " + porcentajeMuerte + ", producción: " + produccion;
        if (valores[0] && !valores[1])
            return "nombre: " + nombre + ", naves: " + cantidadNaves;
        return "nombre: " + nombre;
    }


    @Override
    public void recibirIncursion(Flota flota, Mapa mapa, KonquestFrame frame) throws ListaException {
        PlanetaJugador origen = (PlanetaJugador) flota.getOrigen();
        PlanetaJugador planeta = terraformar(flota);
        Cuadro cuadro = getCuadro();

        activo = false;
        mapa.getPlanetasNeutrales().eliminarContenido(this); // eliminar el planeta de la lista de planetas neutrales del mapa
        planeta.getJugador().getPlanetas().agregar(planeta); // agregar el planeta a la lista de planetas del jugador
        mapa.getPlanetasJugador().agregar(planeta);// agregar el planeta a la lista de planetas de jugadores en el mapa

        cuadro.setPlaneta(planeta);
        cuadro.actualizar();

        frame.agregarFlotaAterrizada("El planeta neutral " + nombre + " ha sido conquistado por el jugador " + origen.getJugador().getNombre() + " con un ataque enviado desde el planeta " + origen.getNombre());

    }

    @Override
    public PlanetaJugador terraformar(Flota flota/*, MotorJuego juego*/) {
        Jugador jugador = ((PlanetaJugador) flota.getOrigen()).getJugador();
        PlanetaJugador planeta = new PlanetaJugador(nombre, flota.getNaves() + cantidadNaves, posicion, porcentajeMuerte, produccion, jugador, jugador.getColor());
        planeta.imagen = flota.getOrigen().getImagen();
        return planeta;
    }
}
