/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.tda.listas.Excepcion.EmptyException;
import controlador.tda.listas.ListaDinamica;
import modelo.Boleto;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class BoletoControl {
    private Boleto boleto = new Boleto();
    private ListaDinamica<Boleto> boletos;

    public BoletoControl(Boleto boleto) {
        this.boleto = boleto;
    }

    public BoletoControl() {
        this.boletos = new ListaDinamica<>();
        
    }
    

   
    public Boolean guardarBoleto() {
        
        try {
            getBoletos().add(getBoleto());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer posVerificar() throws EmptyException {
        
        Integer bandera = 0;

        for (Integer i = 0; i <= this.boletos.getLenght(); i++) {
            
            if (this.getBoletos().getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    public void imprimir() throws EmptyException {
        for (int i = 0; i < this.getBoletos().getLenght(); i++) {
            System.out.println(getBoletos().getInfo(i));
        }
    }

    /**
     * @return the persona
     */
    public Boleto getBoleto() {
        if (boleto == null) {
            boleto = new Boleto();
        }
        return boleto;
    }

    /**
     * @param persona the persona to set
     */
    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public ListaDinamica<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(ListaDinamica<Boleto> boletos) {
        this.boletos = boletos;
    }

    @Override
    public String toString() {
        return "SALIDA: " + getBoleto().getFechaSalida()+ " COMPRA: " + getBoleto().getFechaCompra()+ " VALOR: " + getBoleto().getValor();
    }
}
