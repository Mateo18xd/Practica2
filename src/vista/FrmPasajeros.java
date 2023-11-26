/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.boleto.BoletoControl;
import controlador.DAO.DaoImplement;
import controlador.PasajeroControl;
import controlador.tda.listas.Excepcion.EmptyException;
import controlador.tda.listas.ListaDinamica;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Boleto;
import modelo.Pasajero;
import modelo.VentaBoletos;
import vista.tablas.ModeloTablaBoleto;
import vista.tablas.ModeloTablaPasajero;


public class FrmPasajeros extends javax.swing.JFrame {

    private PasajeroControl pasajeroControl = new PasajeroControl();
    private ModeloTablaPasajero mtp = new ModeloTablaPasajero();
    private controlador.Pasajero.PasajeroControl control = new controlador.Pasajero.PasajeroControl();
    private BoletoControl boletoControl = new BoletoControl();
    private ModeloTablaBoleto mtb = new ModeloTablaBoleto();
    private controlador.boleto.BoletoControl controlb = new controlador.boleto.BoletoControl();
    
    
    private void cargarVista(){
        int fila = tbPasajero.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                pasajeroControl.setPasajero(mtp.getPasajeros().getInfo(fila));
                txtApellidos.setText(pasajeroControl.getPasajero().getApellidos());
                txtDNI.setText(pasajeroControl.getPasajero().getDni());
                txtDNI.setEnabled(false);
                txtNombres.setText(pasajeroControl.getPasajero().getNombres());

            } catch (Exception ex) {
                Logger.getLogger(FrmPasajeros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cargarVistaBoletos(){
        int fila = tbBoleto.getSelectedRow();
        if (fila < 0) {
        JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        try {
            Boleto boletoSeleccionado = mtb.getBoletos().getInfo(fila);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
            
            
            txtSalida.setText(dateFormat.format(boletoSeleccionado.getFechaSalida()));
            txtCompra.setText(dateFormat.format(boletoSeleccionado.getFechaCompra()));
            
            txtDNI.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(FrmPasajeros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    
    
    public void cargarPersonas(ListaDinamica<Pasajero> pasajeros) {
        mtp.setPasajeros(pasajeros);
    }
    
    public void cargarBoletos(ListaDinamica<Boleto> boletos){
        mtb.setBoletos(boletos);
    }

    public Boolean verificar() {
        return (!txtApellidos.getText().trim().isEmpty()
                && !txtNombres.getText().trim().isEmpty()
                && !txtDNI.getText().trim().isEmpty());
                
    }
    public Boolean verificarBoletos() {
        return (!txtSalida.getText().trim().isEmpty()
                && !txtCompra.getText().trim().isEmpty()
                && !txtValor.getText().trim().isEmpty());
                
    }

    private void cargarTabla() {
        mtp.setPasajeros(control.all());
        tbPasajero.setModel(mtp);
        tbPasajero.updateUI();
        mtb.setBoletos(controlb.all());
        tbBoleto.setModel(mtb);
        tbBoleto.updateUI();
    }
    
    private void cargarTablaBoletos() {
        mtb.setBoletos(controlb.all());
        tbBoleto.setModel(mtb);
        tbBoleto.updateUI();
   
}

    
    private double obtenerValor() throws NumberFormatException {
    String valorTexto = txtValor.getText();
    return Double.parseDouble(valorTexto);
}

    
   private void guardarBoleto() throws EmptyException {
    if (verificarBoletos()) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaSalida = dateFormat.parse(txtSalida.getText());
            Date fechaCompra = dateFormat.parse(txtCompra.getText());
            double valor = Double.parseDouble(txtValor.getText());

            Boleto nuevoBoleto = new Boleto(pasajeroControl.getPasajero(), fechaSalida, fechaCompra, valor);

            if (boletoControl.guardarBoleto(nuevoBoleto)) {
                JOptionPane.showMessageDialog(null, "Boleto guardado correctamente");
                limpiar();
                cargarTabla(); 
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el boleto, hubo un error");
            }
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los datos del boleto");
            e.printStackTrace(); 
        }
    } else {
        JOptionPane.showMessageDialog(null, "Falta llenar campos del boleto", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



   private void guardar() throws EmptyException {
    if (verificar()) {
        pasajeroControl.getPasajero().setNombres(txtNombres.getText());
        pasajeroControl.getPasajero().setApellidos(txtApellidos.getText());
        pasajeroControl.getPasajero().setDni(txtDNI.getText());
        if (pasajeroControl.guardar()) {
            control.persist(pasajeroControl.getPasajero());
            JOptionPane.showMessageDialog(null, "Datos guardados");
            cargarTabla();
            limpiar();
            pasajeroControl.setPasajero(null);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
   
  

private void limpiar() {
    txtDNI.setEnabled(true);
    txtApellidos.setText("");
    txtNombres.setText("");
    txtDNI.setText("");
    cargarTabla(); 
    pasajeroControl.setPasajero(null);
}


 private void limpiarCamposBoleto() {
    txtSalida.setText("");
    txtCompra.setText("");
    txtValor.setText("");
    cargarTabla();
    boletoControl.setBoleto(null);
}
 
    public double calcularMontoTotalBoletos(List<Boleto> boletosVendidos) {
        double total = 0.0;
        for (Boleto boleto : boletosVendidos) {
            total += boleto.obtenerValorBoleto();
        }
        return total;
    }
    
     
    
    public void mostrarMontoTotal() {
    List<Boleto> boletosVendidos = boletoControl.obtenerBoletosVendidos();
    double montoTotal = calcularMontoTotalBoletos(boletosVendidos);
        JOptionPane.showMessageDialog(null, "El monto total de boletos vendidos es: " + montoTotal);
       
    }
 

    /**
     * Creates new form GuardarUsuario
     */
    public FrmPasajeros() throws EmptyException {
        initComponents();
        limpiar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbBoleto = new javax.swing.JTable();
        btSeleccionar = new javax.swing.JButton();
        btSalir = new javax.swing.JButton();
        btGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSalida = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCompra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPasajero = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Pasajero");

        jLabel9.setText("Boleto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel3)
                .addGap(273, 273, 273)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel1.setText("DNI:");

        jLabel2.setText("Apellidos:");

        tbBoleto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbBoleto);

        btSeleccionar.setText("Seleccionar");
        btSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSeleccionarActionPerformed(evt);
            }
        });

        btSalir.setText("Salir");
        btSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalirActionPerformed(evt);
            }
        });

        btGuardar.setText("Guardar");
        btGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarActionPerformed(evt);
            }
        });

        jLabel5.setText("Salida:");

        jLabel7.setText("Compra:");

        jLabel8.setText("Valor:");

        tbPasajero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbPasajero);

        jLabel10.setText("Nombre:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(478, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNombres, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(70, 70, 70)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addGap(62, 62, 62)
                                                    .addComponent(jLabel7)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtSalida, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                            .addComponent(txtCompra)
                                            .addComponent(txtValor)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(btGuardar)
                                .addGap(239, 239, 239)
                                .addComponent(btSalir))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(btSeleccionar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(437, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btSeleccionar)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btGuardar)
                    .addComponent(btSalir))
                .addGap(41, 41, 41))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(184, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(95, 95, 95)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarActionPerformed
        try {
            guardar();
            guardarBoleto();
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btGuardarActionPerformed

    private void btSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btSalirActionPerformed

    private void btSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSeleccionarActionPerformed
        cargarVista();
    }//GEN-LAST:event_btSeleccionarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmPasajeros().setVisible(true);
                } catch (EmptyException ex) {
                    Logger.getLogger(FrmPasajeros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGuardar;
    private javax.swing.JButton btSalir;
    private javax.swing.JButton btSeleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbBoleto;
    private javax.swing.JTable tbPasajero;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCompra;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtSalida;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
