/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.tablas;

import controlador.tda.listas.Excepcion.EmptyException;
import controlador.tda.listas.ListaDinamica;
import javax.swing.table.AbstractTableModel;
import modelo.Pasajero;

/**
 *
 * @author Usuario iTC
 */
public class ModeloTablaPasajero extends AbstractTableModel{

    private ListaDinamica<Pasajero> pasajeros;
    
    @Override
    public int getRowCount() {
        return pasajeros.getLenght();   
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Pasajero p = pasajeros.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (p != null) ? p.getNombres() : " ";
                case 1:
                    return (p != null) ? p.getApellidos() : "";
                case 2:
                    return (p != null) ? p.getDni() : "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "NOMBRES";
            case 1:
                return "APELLIDOS";
            case 2:
                return "DNI";
            default:
                return null;
        }
    }

    public ListaDinamica<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(ListaDinamica<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }
    
}
