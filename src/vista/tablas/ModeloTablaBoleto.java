/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.tablas;

import controlador.tda.listas.Excepcion.EmptyException;
import controlador.tda.listas.ListaDinamica;
import javax.swing.table.AbstractTableModel;
import modelo.Boleto;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class ModeloTablaBoleto extends AbstractTableModel {
    private ListaDinamica<Boleto> boletos;
    
    public void setBoletos(ListaDinamica<Boleto> boletos) {
        this.boletos = boletos;
        
    }

    @Override
    public int getRowCount() {
        return boletos.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "FECHASALIDA";
            case 1:
                return "FECHACOMPRA";
            case 2:
                return "VALOR";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       try {
            Boleto b = boletos.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (b != null) ? b.getFechaSalida(): " ";
                case 1:
                    return (b != null) ? b.getFechaCompra(): "";
                case 2:
                    return (b != null) ? b.getValor(): "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
       
       
    }
    public ListaDinamica<Boleto> getBoletos() {
        return boletos;
    }
    
     
}
