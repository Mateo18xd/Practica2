/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.List; 
import java.util.ArrayList;
/**
 *
 * @author Usuario iTC
 */
public class VentaBoletos {
    private int totalBoletosVendidos;
    private final List<Boleto> boletosVendidos;

    public VentaBoletos(int totalBoletosVendidos) {
        this.totalBoletosVendidos = totalBoletosVendidos;
        this.boletosVendidos = new ArrayList<>();
    }

    public void agregarBoletoVendido(Boleto boleto) {
        boletosVendidos.add(boleto);
    }

    
}


