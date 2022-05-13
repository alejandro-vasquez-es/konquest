/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import com.alejandrov.backend.MotorJuego;
import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.planetas.FilaPlanetaJugador;
import com.alejandrov.frontend.planetas.Planeta;
import com.alejandrov.frontend.planetas.PlanetaJugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Random;

/**
 * @author aleja
 */
public class ConfiguracionPlanetasJugadorFrame extends javax.swing.JFrame {

    private DefaultTableModel model;
    private JFrame parent;
    private MotorJuego juego;
    private Lista<String> nombresPlaneta;
    private Jugador[] jugadores;

    public ConfiguracionPlanetasJugadorFrame(JFrame parent, MotorJuego juego) {
        this.juego = juego;
        this.parent = parent;
        this.nombresPlaneta = new Lista<String>();
        initComponents();

        jugadores = juego.getJugadores();

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 6 && row <= jugadores.length - 1)
                    return false;
                return column != 0 && column != 7;
            }

            @Override
            public Object getValueAt(int row, int column) {
                if (column == 7) {
                    String nombreJugador = getValueAt(row, 6).toString();
                    String color = buscarColorJugador(nombreJugador);
                    return color;
                } else {
                    return super.getValueAt(row, column);
                }
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                fireTableCellUpdated(row, 7);
            }
        };
        initTablaPlanetas();
    }

    public String buscarColorJugador(String nombre) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getNombre().equals(nombre))
                return jugadores[i].getColor();
        }
        return null;
    }

    public void initTablaPlanetas() {

        model.addColumn("nombre");
        model.addColumn("cantidad de naves");
        model.addColumn("producción");
        model.addColumn("porcentaje de muertes");
        model.addColumn("columna");
        model.addColumn("fila");
        model.addColumn("conquistador");
        model.addColumn("tipo de planeta");

        tablaPlanetas.setModel(model);
        tablaPlanetas.setRowHeight(22);
        tablaPlanetas.getColumnModel().getColumn(3).setPreferredWidth(125);
        tablaPlanetas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaPlanetas.getColumnModel().getColumn(7).setPreferredWidth(85);

        TableColumn columnaConquistador = tablaPlanetas.getColumnModel().getColumn(6);
        JComboBox comboBoxConquistador = new JComboBox<>();
        for (int i = 0; i < jugadores.length; i++) {
            comboBoxConquistador.addItem(jugadores[i].getNombre());
        }
        columnaConquistador.setCellEditor(new DefaultCellEditor(comboBoxConquistador));

        for (int i = 0; i < jugadores.length; i++) {
            String nombre = generarNombre();
            model.addRow(new Object[]{nombre, 8, 8, 0.4, 5, 5, jugadores[i].getNombre(), jugadores[i].getColor()});
        }
    }

    public void agregarPlaneta() {
        String nombre = generarNombre();
        model.addRow(new Object[]{nombre, 8, 8, 0.4, 5, 5, jugadores[0].getNombre(), jugadores[0].getColor()});
    }

//    private void crearPlaneta() {
//        String nombre = generarNombre();
//        int cantidadNaves = 0;
//        double porcentajeMuerte = 0;
//        int produccion = 0;
////        ImageIcon imagen
////        Jugador jugador
//        String tipo
//        PlanetaJugador planeta = new PlanetaJugador(nombre,cantidadNaves,posicion,porcentajeMuerte, produccion, jugador, tipo);
//    }

    private String generarNombre() {
        Random random = new Random();
        char s1 = (char) (random.nextInt(26) + 'a');
        char s2 = (char) (random.nextInt(26) + 'a');
        char s3 = (char) (random.nextInt(26) + 'a');
        String nombre = "" + s1 + s2 + s3;
        while (nombresPlaneta.incluye(nombre)) {
            nombre = "" + s1 + s2 + s3;
        }
        nombresPlaneta.agregar(nombre);
        return nombre.toUpperCase();
    }

    public void empezarJuego() throws ValidacionesException, ListaException {
        int totalPlanetas = model.getRowCount();
        juego.getMapa().reiniciarListaPlanetas();
        FilaPlanetaJugador[] filaPlanetas = new FilaPlanetaJugador[totalPlanetas];
        for (int i = 0; i < totalPlanetas; i++) {
            String nombre = model.getValueAt(i, 0).toString();
            int cantidadNaves = Integer.parseInt(model.getValueAt(i, 1).toString());
            int producción = Integer.parseInt(model.getValueAt(i, 2).toString());
            double porcentajeMuertes =  Double.parseDouble(model.getValueAt(i, 3).toString());
            int columnaPosicion = Integer.parseInt(model.getValueAt(i, 4).toString());
            int filaPosicion = Integer.parseInt(model.getValueAt(i, 5).toString());
            String conquistador = model.getValueAt(i, 6).toString();
            String tipo = model.getValueAt(i, 7).toString();
            filaPlanetas[i] = new FilaPlanetaJugador(i, nombre, cantidadNaves, producción, porcentajeMuertes, columnaPosicion, filaPosicion, conquistador, tipo, this, juego.getMapa());
        }
        for (FilaPlanetaJugador filaPlanetaJugador :
                filaPlanetas) {
            filaPlanetaJugador.validar();
            PlanetaJugador nuevoPlanetaJugador = new PlanetaJugador(filaPlanetaJugador.getNombre(),filaPlanetaJugador.getCantidadNaves(),filaPlanetaJugador.getPosicion(),filaPlanetaJugador.getPorcentajeMuertes(),filaPlanetaJugador.getProducción(),buscarJugador(filaPlanetaJugador.getNombre()), filaPlanetaJugador.getTipo());
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaJugador);
        }
            System.out.println("si se pudo validar");
    }

    public Jugador buscarJugador(String nombre) {
        for (int i = 0; i < juego.getJugadores().length; i++) {
            if(juego.getJugadores()[i].getNombre().equals(nombre)){
                return juego.getJugadores()[i];
            }
        }
        return null;
    }

    @Override
    public void dispose() {
        parent.setEnabled(true);
        ((MapaDesignFrame) parent).reiniciarIndiceColores();
        super.dispose();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPlanetas = new javax.swing.JTable();
        buttons = new javax.swing.JPanel();
        agregarPlanetaButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("planetas de jugadores");
        setPreferredSize(new java.awt.Dimension(750, 400));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Configuración Planetas de Jugadores");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 5, 1));
        jLabel1.setIconTextGap(10);
        jLabel1.setPreferredSize(new java.awt.Dimension(326, 52));
        getContentPane().add(jLabel1);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(600, 430));

        tablaPlanetas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaPlanetas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaPlanetas);

        getContentPane().add(jScrollPane2);

        buttons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        agregarPlanetaButton.setText("Agregar Planeta");
        agregarPlanetaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPlanetaButtonActionPerformed(evt);
            }
        });
        buttons.add(agregarPlanetaButton);

        jButton1.setText("Empezar Juego");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        buttons.add(jButton1);

        getContentPane().add(buttons);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarPlanetaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPlanetaButtonActionPerformed
        agregarPlaneta();
    }//GEN-LAST:event_agregarPlanetaButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            empezarJuego();
        } catch (ValidacionesException e) {
            e.printStackTrace();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarPlanetaButton;
    private javax.swing.JPanel buttons;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaPlanetas;
    // End of variables declaration//GEN-END:variables
}
