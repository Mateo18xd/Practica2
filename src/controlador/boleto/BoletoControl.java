/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.boleto;

import controlador.DAO.DaoImplement;
import controlador.tda.listas.ListaDinamica;
import java.util.List;
import modelo.Boleto;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class BoletoControl extends DaoImplement<Boleto> {
    
    private ListaDinamica<Boleto> boletos;
    private Boleto boleto;

    public BoletoControl() {
        super(Boleto.class);
    }
    
     public ListaDinamica<Boleto> getBoletos() {
        boletos = all();
        return boletos;
    }

    public void setBoletos(ListaDinamica<Boleto> boletos) {
        this.boletos = boletos;
    }

    public Boleto getBoleto() {
        if (boleto == null) {
            boleto = new Boleto();
        }
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }
    
    public Boolean persist(){
       
        return persist(boleto);
    }

    public boolean guardarBoleto(Boleto nuevoBoleto) {
      try {
        modelo.Boleto miBoleto = new modelo.Boleto(); 
        BoletoControl miControlador = new BoletoControl();
        miControlador.guardarBoleto(miBoleto);

                       boletos.add(nuevoBoleto);
            return true; 
        } catch (Exception e) {
            return false; 
        }
       
      
    }

    public List<Boleto> obtenerBoletosVendidos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   


    
  
   

  
}
