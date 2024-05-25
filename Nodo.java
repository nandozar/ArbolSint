/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

abstract class Nodo {
    public abstract String getValor();
}

class NodoHoja extends Nodo {
    private String valor;

    public NodoHoja(String valor) {
        this.valor = valor;
    }

    @Override
    public String getValor() {
        return valor;
    }
}

class NodoOperador extends Nodo {
    private String operador;
    private Nodo izquierdo, derecho;

    public NodoOperador(String operador, Nodo izquierdo, Nodo derecho) {
        this.operador = operador;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    @Override
    public String getValor() {
        return operador;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }
}


