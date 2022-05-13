package com.alejandrov.backend.listas;

public class Lista<T> {

    private Nodo<T> inicio;
    private Nodo<T> ultimo;
    private int longitud = 0;

    public void agregar(T contenido) {
        if (esVacia()) {
            inicio = new Nodo<>(contenido);
            ultimo = inicio;
        } else {
            Nodo<T> nuevo = new Nodo<>(contenido);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        longitud++;
    }

    public boolean esVacia() {
        return inicio == null;
    }

    public int obtenerLongitud() {
        return longitud;
    }

    public boolean incluye(T contenido) {
        if (!esVacia()) {
            Nodo<T> actual = inicio;
            for (int i = 0; i < longitud; i++) {
                if (actual.getContenido().equals(contenido))
                    return true;
                Nodo<T> siguiente = actual.getSiguiente();
                actual = siguiente;
            }
        }
        return false;
    }

    public void eliminarContenido(T contenido) throws ListaException {
        int indice = obtenerIndice(contenido);
        eliminarElementoEnIndice(indice);
    }

    public int obtenerIndice(T contenido) {
        int indice = 0;
        Nodo<T> actual = inicio;
        for (int i = 0; i < longitud; i++) {
            if (actual.getContenido().equals(contenido))
                return indice;
            Nodo<T> siguiente = actual.getSiguiente();
            actual = siguiente;
            indice++;
        }
        return -1;
    }

    public void eliminarElementoEnIndice(int indice) throws ListaException {
        if (indice >= longitud || indice < 0) {
            throw new ListaException("Indice fuera de rango");
        }

        if (indice == 0) {
            inicio = inicio.getSiguiente();
        } else if (indice == longitud - 1) {
            eliminarUltimo();
        } else {
            Nodo<T> anterior = buscarIndice(indice - 1);
            Nodo<T> siguiente = anterior.getSiguiente().getSiguiente();
            anterior.setSiguiente(siguiente);
        }
        longitud--;

    }

    public void eliminarUltimo() throws ListaException {
        if (esVacia()) {
            throw new ListaException("La lista esta vacia!");
        } else if (longitud == 1) {
            inicio = null;
            ultimo = null;
        } else {
            Nodo<T> nuevoUltimo = buscarIndice(longitud - 2);
            nuevoUltimo.setSiguiente(null);
            ultimo = nuevoUltimo;
        }
        longitud--;
    }

    public T obtenerContenido(int indice) throws ListaException {
        return buscarIndice(indice).getContenido();
    }

    public Nodo<T> buscarIndice(int indice) throws ListaException {
        if (indice >= longitud || indice < 0) {
            throw new ListaException("Indice fuera de rango");
        }

        Nodo<T> actual = inicio;
        for (int i = 0; i < indice; i++) {
            Nodo<T> siguiente = actual.getSiguiente();
            actual = siguiente;
        }

        return actual;
    }
}