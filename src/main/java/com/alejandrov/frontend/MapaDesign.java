/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author aleja
 */
public class MapaDesign extends javax.swing.JFrame {

    /**
     * Creates new form MapaDesign
     */
    public MapaDesign() {
        initComponents();
        initTablaJugadores();
    }
    
    public void initTablaJugadores() {
        DefaultTableModel model = new DefaultTableModel();        
        
        model.addColumn("nombre");
        model.addColumn("tipo");
        
        model.addRow(new Object[]{"Jugador 0", "Humano"});
        model.addRow(new Object[]{"Jugador 1", "IA (fácil)"});
        
        tablaJugadores.setModel(model);
        
        TableColumn columnaTipo = tablaJugadores.getColumnModel().getColumn(1);
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Humano");
        comboBox.addItem("IA (difícil)");
        comboBox.addItem("IA (fácil)");
        
        columnaTipo.setCellEditor(new DefaultCellEditor(comboBox));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        North = new javax.swing.JPanel();
        jugadores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaJugadores = new javax.swing.JTable();
        buttons = new javax.swing.JPanel();
        AñadirButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        Mapa = new javax.swing.JPanel();
        labelAndSpinner = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        planetasNeutralesSpinner = new javax.swing.JSpinner();
        labelAndSpinner1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        anchuraSpinner = new javax.swing.JSpinner();
        labelAndSpinner2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        alturaSpinner = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        dueño = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        porcentajeMuerte = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        producción = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Opciones = new javax.swing.JPanel();
        mapaCiegoCheckbox = new javax.swing.JCheckBox();
        produccionAcumuladaCheckbox = new javax.swing.JCheckBox();
        produccionAcumuladaCheckbox1 = new javax.swing.JCheckBox();
        neutrales = new javax.swing.JPanel();
        mostrarNavesCheckbox = new javax.swing.JCheckBox();
        mostrarEstadísticasCheckbox = new javax.swing.JCheckBox();
        producción1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        South = new javax.swing.JPanel();
        aceptarButton = new javax.swing.JButton();
        cancelarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Diseño de mapa");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        North.setPreferredSize(new java.awt.Dimension(366, 360));
        North.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 22, 20));

        jugadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Jugadores"));
        jugadores.setPreferredSize(new java.awt.Dimension(200, 320));
        jugadores.setLayout(new javax.swing.BoxLayout(jugadores, javax.swing.BoxLayout.Y_AXIS));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(180, 280));

        tablaJugadores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaJugadores);

        jugadores.add(jScrollPane2);

        buttons.setPreferredSize(new java.awt.Dimension(172, 35));

        AñadirButton.setText("Añadir");
        buttons.add(AñadirButton);

        eliminarButton.setText("Eliminar");
        buttons.add(eliminarButton);

        jugadores.add(buttons);

        North.add(jugadores);

        Mapa.setBorder(javax.swing.BorderFactory.createTitledBorder("Mapa"));
        Mapa.setPreferredSize(new java.awt.Dimension(200, 320));
        Mapa.setLayout(new javax.swing.BoxLayout(Mapa, javax.swing.BoxLayout.Y_AXIS));

        labelAndSpinner.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Planetas neutrales:");
        labelAndSpinner.add(jLabel1);

        planetasNeutralesSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        labelAndSpinner.add(planetasNeutralesSpinner);

        Mapa.add(labelAndSpinner);

        labelAndSpinner1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Anchura:");
        labelAndSpinner1.add(jLabel2);

        anchuraSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        labelAndSpinner1.add(anchuraSpinner);

        Mapa.add(labelAndSpinner1);

        labelAndSpinner2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setText("Altura");
        labelAndSpinner2.add(jLabel3);

        alturaSpinner.setMinimumSize(new java.awt.Dimension(40, 40));
        labelAndSpinner2.add(alturaSpinner);

        Mapa.add(labelAndSpinner2);
        Mapa.add(jSeparator1);

        dueño.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setText("Dueño:");
        dueño.add(jLabel4);

        jTextField1.setPreferredSize(new java.awt.Dimension(100, 28));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        dueño.add(jTextField1);

        Mapa.add(dueño);

        porcentajeMuerte.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText("Pocentaje de muertes:");
        porcentajeMuerte.add(jLabel5);

        jTextField2.setPreferredSize(new java.awt.Dimension(30, 28));
        jTextField2.setRequestFocusEnabled(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        porcentajeMuerte.add(jTextField2);

        Mapa.add(porcentajeMuerte);

        producción.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setText("Producción:");
        producción.add(jLabel6);

        jTextField3.setPreferredSize(new java.awt.Dimension(30, 28));
        jTextField3.setRequestFocusEnabled(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        producción.add(jTextField3);

        Mapa.add(producción);

        jButton1.setText("Al azar");
        Mapa.add(jButton1);

        North.add(Mapa);

        Opciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));
        Opciones.setPreferredSize(new java.awt.Dimension(200, 320));
        Opciones.setLayout(new javax.swing.BoxLayout(Opciones, javax.swing.BoxLayout.Y_AXIS));

        mapaCiegoCheckbox.setText("Mapa ciego");
        Opciones.add(mapaCiegoCheckbox);

        produccionAcumuladaCheckbox.setText("Producción acumulativa");
        produccionAcumuladaCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produccionAcumuladaCheckboxActionPerformed(evt);
            }
        });
        Opciones.add(produccionAcumuladaCheckbox);

        produccionAcumuladaCheckbox1.setText("Producción tras la captura");
        produccionAcumuladaCheckbox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produccionAcumuladaCheckbox1ActionPerformed(evt);
            }
        });
        Opciones.add(produccionAcumuladaCheckbox1);

        neutrales.setBorder(javax.swing.BorderFactory.createTitledBorder("Neutrales"));
        neutrales.setLayout(new javax.swing.BoxLayout(neutrales, javax.swing.BoxLayout.Y_AXIS));

        mostrarNavesCheckbox.setText("Mostrar naves");
        neutrales.add(mostrarNavesCheckbox);

        mostrarEstadísticasCheckbox.setText("Mostrar estadísticas");
        neutrales.add(mostrarEstadísticasCheckbox);

        producción1.setPreferredSize(new java.awt.Dimension(110, 15));
        producción1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setText("Producción:");
        producción1.add(jLabel7);

        jTextField4.setPreferredSize(new java.awt.Dimension(30, 28));
        jTextField4.setRequestFocusEnabled(false);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        producción1.add(jTextField4);

        neutrales.add(producción1);

        Opciones.add(neutrales);

        North.add(Opciones);

        getContentPane().add(North);

        South.setPreferredSize(new java.awt.Dimension(700, 40));
        South.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        aceptarButton.setText("Aceptar");
        South.add(aceptarButton);

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });
        South.add(cancelarButton);

        getContentPane().add(South);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void produccionAcumuladaCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produccionAcumuladaCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_produccionAcumuladaCheckboxActionPerformed

    private void produccionAcumuladaCheckbox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produccionAcumuladaCheckbox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_produccionAcumuladaCheckbox1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarButtonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AñadirButton;
    private javax.swing.JPanel Mapa;
    private javax.swing.JPanel North;
    private javax.swing.JPanel Opciones;
    private javax.swing.JPanel South;
    private javax.swing.JButton aceptarButton;
    private javax.swing.JSpinner alturaSpinner;
    private javax.swing.JSpinner anchuraSpinner;
    private javax.swing.JPanel buttons;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JPanel dueño;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel jugadores;
    private javax.swing.JPanel labelAndSpinner;
    private javax.swing.JPanel labelAndSpinner1;
    private javax.swing.JPanel labelAndSpinner2;
    private javax.swing.JCheckBox mapaCiegoCheckbox;
    private javax.swing.JCheckBox mostrarEstadísticasCheckbox;
    private javax.swing.JCheckBox mostrarNavesCheckbox;
    private javax.swing.JPanel neutrales;
    private javax.swing.JSpinner planetasNeutralesSpinner;
    private javax.swing.JPanel porcentajeMuerte;
    private javax.swing.JCheckBox produccionAcumuladaCheckbox;
    private javax.swing.JCheckBox produccionAcumuladaCheckbox1;
    private javax.swing.JPanel producción;
    private javax.swing.JPanel producción1;
    private javax.swing.JTable tablaJugadores;
    // End of variables declaration//GEN-END:variables
}
