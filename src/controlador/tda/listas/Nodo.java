/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.tda.listas;

/**
 *
 * @author Usuario iTC
 */
public class Nodo <E> {
    private E info;
    private Nodo<E> next;
    
    public Nodo(E info) {
        this.info = info;
        next = null;
    }

    public Nodo(E info, Nodo<E> next) {
        this.info = info;
        this.next = next;
    }

    public Nodo() {
        next = null;
        info = null;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Nodo<E> getNext() {
        return next;
    }

    public void setNext(Nodo<E> next) {
        this.next = next;
    }
    
    
    
    
}
