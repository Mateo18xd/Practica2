/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.tda.listas;

import controlador.tda.listas.Excepcion.EmptyException;



/**
 *
 * @author Usuario iTC
 */
public class ListaDinamica<E> {
    
   private Nodo<E> header;
    private Nodo<E> last;
    private Integer lenght;
    
    public ListaDinamica() {
        header = null;
        last = null;
        lenght = 0;
    }
    
    public Nodo<E> getHeader() {
        return header;
    }

    public void setHeader(Nodo<E> header) {
        this.header = header;
    }

    public Nodo<E> getLast() {
        return last;
    }

    public void setLast(Nodo<E> last) {
        this.last = last;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }
    
    public Boolean isEmpty() {
        return header == null || getLenght() == 0;
    }

    private void addFirst(E info) {
        Nodo<E> help;
        if (isEmpty()) {
            help = new Nodo<>(info);
            header = help;
            last = help;
            
        } else {
            Nodo<E> headHelp = header;
            help = new Nodo<>(info, headHelp);
            header = help;
            
        }
        lenght++;
    }

    private void addLast(E info) {
        Nodo<E> help;
        if (isEmpty()) {
            addFirst(info);
        } else {
            help = new Nodo<>(info, null);
            last.setNext(help);
            last = help;
            lenght++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    public void add(E info, Integer index) throws EmptyException, IndexOutOfBoundsException {
        if (index.intValue() == 0) {
            addFirst(info);
        } else if (index.intValue() == lenght.intValue()) {
            addLast(info);
        } else {
            Nodo<E> search_preview = getNode(index - 1);
            Nodo<E> search = getNode(index);
            Nodo<E> help = new Nodo<>(info, search);
            search_preview.setNext(help);
            setLenght((Integer) (getLenght()+1));
        }
    }

    private E getFirst() throws EmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        }
        return header.getInfo();
    }

    public E getInfo(Integer index) throws EmptyException {
        return getNode(index).getInfo();
    }

    private Nodo<E> getNode(Integer index) throws EmptyException{
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return header;
        } else if (index== (lenght - 1)) {
            return last;
        } else {
            Nodo<E> search = header;
            Integer cont = 0;
            while (cont < index) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E merge(E data, Integer pos) throws EmptyException{
        Nodo<E> node = getNode(pos);
        E oldData = node.getInfo();
        node.setInfo(data);
        return oldData;
    }
    
    public E extractFirst() throws EmptyException{
        if(isEmpty()){
            throw new EmptyException("List empty");
        }else{
            E element = header.getInfo();
            Nodo<E> help = header.getNext();
            header = null;
            header = help;
            if(lenght == 1)
                last = null;
            lenght--;
            return element;
        }
    }
    
    
    public E extractLast() throws EmptyException{
        if(isEmpty()){
            throw new EmptyException("List empty");
        }else{
            E element = last.getInfo();
            Nodo<E> help = getNode(lenght - 2);
            if(help == null){
                last = null;
                if(lenght == 2){
                    last = header;
                }else{
                    header = null;
                }
                }else{
                
                last = null;
                last = help;
                last.setNext(null);
                }
                lenght--;
            return element;
        }
    }
    
    public E extract(Integer index) throws EmptyException{
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return extractFirst();
        } else if (index== (lenght - 1)) {
            return extractLast();
        } else {
            Nodo<E> node_preview = getNode(index - 1);
            Nodo<E> node_actually = getNode(index);
            E info = node_actually.getInfo();
            Nodo<E> help_next = node_actually.getNext();
            node_actually = null;
            node_preview.setNext(help_next);
            lenght--;
            return info;
            
        }
        
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Lista Data\n");
        try {
            Nodo<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append("\n");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getApellidos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
