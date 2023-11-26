/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.tda.listas.Excepcion.EmptyException;
import controlador.tda.listas.ListaDinamica;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class PasajeroControl {
    private Pasajero pasajero = new Pasajero();
    private ListaDinamica<Pasajero> pasajeros;

    public PasajeroControl(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public PasajeroControl() {
        this.pasajeros = new ListaDinamica<>();
        
    }
    

   
    public Boolean guardar() {
        
        try {
            getPasajeros().add(getPasajero());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer posVerificar() throws EmptyException {
        
        Integer bandera = 0;

        for (Integer i = 0; i <= this.pasajeros.getLenght(); i++) {
            
            if (this.getPasajeros().getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    public void imprimir() throws EmptyException {
        for (int i = 0; i < this.getPasajeros().getLenght(); i++) {
            System.out.println(getPasajeros().getInfo(i));
        }
    }

    /**
     * @return the persona
     */
    public Pasajero getPasajero() {
        if (pasajero == null) {
            pasajero = new Pasajero();
        }
        return pasajero;
    }

    /**
     * @param persona the persona to set
     */
    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public ListaDinamica<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(ListaDinamica<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    @Override
    public String toString() {
        return "DNI: " + getPasajero().getDni() + " Apellidos: " + getPasajeros().getApellidos() + " Nombres: " + getPasajero().getNombres();
    }
}
