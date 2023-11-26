/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Pasajero;

import controlador.DAO.DaoImplement;
import controlador.tda.listas.ListaDinamica;
import static java.util.random.RandomGeneratorFactory.all;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class PasajeroControl extends DaoImplement<Pasajero> {
    private ListaDinamica<Pasajero> pasajeros;
    private Pasajero pasajero;

    public PasajeroControl() {
        super(Pasajero.class);
    }

    public ListaDinamica<Pasajero> getPasajeros() {
        pasajeros = all();
        return pasajeros;
    }

    public void setPasajeros(ListaDinamica<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Pasajero getPasajero() {
        if (pasajero == null) {
            pasajero = new Pasajero();
        }
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
    public Boolean persist(){
       
        return persist(pasajero);
    }
}
