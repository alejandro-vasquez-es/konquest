package com.alejandrov.backend.interfaces;

import com.alejandrov.backend.Flota;
import com.alejandrov.backend.Mapa;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.KonquestFrame;

public interface RecibirIncursion {
    public void recibirIncursion(Flota flota, Mapa mapa, KonquestFrame frame) throws ListaException;
}
