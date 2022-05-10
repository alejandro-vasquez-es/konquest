/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import java.awt.*;
import java.sql.SQLOutput;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * @author aleja
 */
public class Konquest extends javax.swing.JFrame {

    /**
     * Creates new form Konquest
     */

    final private int ALTURA_MAIN;

    public Konquest() {
        initComponents();
        ALTURA_MAIN = Center.getHeight();
        crearCuadricula();
        //Messages.setVisible(false);
    }

    public static void scaleImage(ImageIcon icon, JLabel label) {
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    public void crearCuadricula() {
        JPanel cuadricula = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int filas = 10;
        int columnas = 10;

        //cuadricula.setMinimumSize(minimumSize);
        Border lineaNegra = BorderFactory.createLineBorder(Color.DARK_GRAY);
        cuadricula.setBorder(lineaNegra);
        cuadricula.setBackground(Color.lightGray);

        int ladoCuadro = (int) Math.floor(ALTURA_MAIN / filas);
        cuadricula.setMinimumSize(new Dimension(ladoCuadro * columnas, ladoCuadro * filas));
        cuadricula.setMaximumSize(new Dimension(ladoCuadro * columnas, ladoCuadro * filas));


        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < filas; j++) {
                JLabel cuadro = new JLabel();
                System.out.println(ladoCuadro);
                cuadro.setPreferredSize(new Dimension(ladoCuadro, ladoCuadro));
                cuadro.setBorder(lineaNegra);
                c.fill = GridBagConstraints.BOTH;
//                c.ipady = ladoCuadro;
                c.gridx = i;
                c.gridy = j;
//                cuadricula.setPreferredSize(new Dimension(ladoCuadro, ladoCuadro));
                cuadricula.add(cuadro, c);
            }
        }

        Center.add(cuadricula);
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
        menuAcciones = new javax.swing.JPanel();
        nuevoButton = new javax.swing.JButton();
        terminarJuegoButton = new javax.swing.JButton();
        terminarTurno = new javax.swing.JButton();
        calcularDistanciaButton = new javax.swing.JButton();
        consultaFlotaButton = new javax.swing.JButton();
        menuInstrucciones = new javax.swing.JPanel();
        instruccionLabel = new javax.swing.JLabel();
        Right = new javax.swing.JPanel();
        navesMandarLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        terminarTurnoButton = new javax.swing.JButton();
        Center = new javax.swing.JPanel();
        Messages = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Konquest");
        setPreferredSize(new java.awt.Dimension(1100, 620));
        getContentPane().setLayout(new java.awt.BorderLayout(15, 15));

        North.setPreferredSize(new java.awt.Dimension(100, 80));
        North.setLayout(new javax.swing.BoxLayout(North, javax.swing.BoxLayout.Y_AXIS));

        menuAcciones.setPreferredSize(new java.awt.Dimension(1100, 38));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        menuAcciones.setLayout(flowLayout1);

        nuevoButton.setText("Nuevo");
        nuevoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoButtonActionPerformed(evt);
            }
        });
        menuAcciones.add(nuevoButton);

        terminarJuegoButton.setText("Terminar Juego");
        menuAcciones.add(terminarJuegoButton);

        terminarTurno.setText("Terminar turno");
        menuAcciones.add(terminarTurno);

        calcularDistanciaButton.setText("Calcular distancia");
        menuAcciones.add(calcularDistanciaButton);

        consultaFlotaButton.setText("Consulta de flota");
        menuAcciones.add(consultaFlotaButton);

        North.add(menuAcciones);

        menuInstrucciones.setBackground(new java.awt.Color(167, 167, 167));
        menuInstrucciones.setPreferredSize(new java.awt.Dimension(1100, 42));
        menuInstrucciones.setLayout(new java.awt.BorderLayout());
        menuInstrucciones.add(instruccionLabel, java.awt.BorderLayout.LINE_START);

        Right.setBackground(new java.awt.Color(167, 167, 167));
        Right.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        navesMandarLabel.setText("Naves a mandar:");
        Right.add(navesMandarLabel);

        jTextField1.setEnabled(false);
        jTextField1.setPreferredSize(new java.awt.Dimension(60, 28));
        Right.add(jTextField1);

        terminarTurnoButton.setText("Terminar turno");
        Right.add(terminarTurnoButton);

        menuInstrucciones.add(Right, java.awt.BorderLayout.LINE_END);

        North.add(menuInstrucciones);

        getContentPane().add(North, java.awt.BorderLayout.PAGE_START);

        Center.setBorder(null);
        Center.setPreferredSize(new java.awt.Dimension(1100, 200));
        getContentPane().add(Center, java.awt.BorderLayout.CENTER);

        Messages.setBorder(javax.swing.BorderFactory.createTitledBorder("Mensajes"));

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout MessagesLayout = new javax.swing.GroupLayout(Messages);
        Messages.setLayout(MessagesLayout);
        MessagesLayout.setHorizontalGroup(
            MessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
        );
        MessagesLayout.setVerticalGroup(
            MessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );

        getContentPane().add(Messages, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoButtonActionPerformed
        MapaDesign mapaDesign = new MapaDesign();
        mapaDesign.setVisible(true);
    }//GEN-LAST:event_nuevoButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Center;
    private javax.swing.JPanel Messages;
    private javax.swing.JPanel North;
    private javax.swing.JPanel Right;
    private javax.swing.JButton calcularDistanciaButton;
    private javax.swing.JButton consultaFlotaButton;
    private javax.swing.JLabel instruccionLabel;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel menuAcciones;
    private javax.swing.JPanel menuInstrucciones;
    private javax.swing.JLabel navesMandarLabel;
    private javax.swing.JButton nuevoButton;
    private javax.swing.JButton terminarJuegoButton;
    private javax.swing.JButton terminarTurno;
    private javax.swing.JButton terminarTurnoButton;
    // End of variables declaration//GEN-END:variables
}
