package com.alejandrov.backend.interfaces;

import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.ValidacionesException;

public interface Validar {
    public void validar() throws ValidacionesException, ListaException;
}
