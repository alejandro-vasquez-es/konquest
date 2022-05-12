package com.alejandrov.backend.enums;

public enum TiposJugador {
    HUMANO("Humano"),
    IA_DIFICIL("IA (difícil)"),
    IA_FACIL("IA (fácil)") ;

     final public String valor;

    TiposJugador(String valor) {
        this.valor = valor;
    }
}
