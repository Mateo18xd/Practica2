/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO;

import com.thoughtworks.xstream.XStream;
import controlador.tda.listas.ListaDinamica;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Usuario iTC
 */
public class DaoImplement<T> implements DaoInterface<T>{
    
    private Class<T> clazz;
    private XStream conection;
    private String URL;
    
     public DaoImplement(Class<T> clazz) {
        this.clazz = clazz;
        conection = Bridge.getConection();
        URL = Bridge.URL + clazz.getSimpleName() + ".json";
    }

    @Override
    public Boolean persist(T data) {
     ListaDinamica<T> ld = all();
        ld.add(data);
        try {
            this.conection.toXML(ld, new FileWriter(URL));
            return true;
        } catch (Exception e) {
            return false;
        }    
    }

    @Override
    public Boolean merge(T data, Integer index) {
     ListaDinamica<T> ld = all();
        try {
            ld.add(data, index); 
            this.conection.toXML(ld, new FileWriter(URL));
            return true;
        } catch (Exception e) {
            return false;
        } 
    }

    @Override
    public ListaDinamica<T> all() {
     ListaDinamica<T> dl = new ListaDinamica<>();
        try {
            dl = (ListaDinamica<T>)conection.fromXML(new FileReader(URL));
        } catch (Exception e) {
        }
        return dl;   
    }

    @Override
    public T get(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
