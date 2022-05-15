/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.alejandrov.frontend;

import com.alejandrov.backend.MotorJuego;
import com.alejandrov.backend.Posicion;
import com.alejandrov.backend.jugador.Jugador;
import com.alejandrov.backend.listas.Lista;
import com.alejandrov.backend.listas.ListaException;
import com.alejandrov.frontend.planetas.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Random;

/**
 * @author aleja
 */
public class ConfiguracionPlanetasJugadorFrame extends javax.swing.JFrame {

    private DefaultTableModel modelTablaJugadores;
    private DefaultTableModel modelTablaNeutrales;
    private JFrame parent;
    private MotorJuego juego;
    private Lista<String> nombresPlaneta;
    private Jugador[] jugadores;
    private KonquestFrame konquestFrame;

    public ConfiguracionPlanetasJugadorFrame(JFrame parent, MotorJuego juego, KonquestFrame konquestFrame) {
        this.juego = juego;
        this.parent = parent;
        this.konquestFrame = konquestFrame;
        this.nombresPlaneta = new Lista<String>();
        initComponents();

        jugadores = juego.getJugadores();

        initTablaPlanetas();
        if (juego.getMapa().esAlAzar()) {
            buttons.add(empezarJuegoButton);
            buttons.add(cancelarButton);
            planetasNeutrales.setVisible(false);
            buttons1.setVisible(false);
        } else {
            initTablaNeutrales();
        }
    }

    public String buscarColorJugador(String nombre) {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getNombre().equals(nombre))
                return jugadores[i].getColor();
        }
        return null;
    }

    public void initTablaNeutrales() {

        modelTablaNeutrales = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column != 0);
            }

        };

        modelTablaNeutrales.addColumn("nombre");
        modelTablaNeutrales.addColumn("cantidad de naves");
        modelTablaNeutrales.addColumn("producción");
        modelTablaNeutrales.addColumn("porcentaje de muertes");
        modelTablaNeutrales.addColumn("columna");
        modelTablaNeutrales.addColumn("fila");

        tablaNeutrales.setModel(modelTablaNeutrales);
        tablaNeutrales.setRowHeight(22);
        tablaNeutrales.getColumnModel().getColumn(3).setPreferredWidth(125);

        int planetasNeutrales = juego.getMapa().getTotalPlanetasNeutrales();
        for (int i = 0; i < planetasNeutrales; i++) {
            String nombre = generarNombre();
            modelTablaNeutrales.addRow(new Object[]{nombre, 10, 5, 0.2, 0, 0});
        }

    }

    public void initTablaPlanetas() {

        modelTablaJugadores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (juego.getMapa().esAlAzar()) {
                    if (column == 6 && row > jugadores.length - 1) {
                        return true;
                    }
                    return false;
                }
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

        modelTablaJugadores.addColumn("nombre");
        modelTablaJugadores.addColumn("cantidad de naves");
        modelTablaJugadores.addColumn("producción");
        modelTablaJugadores.addColumn("porcentaje de muertes");
        modelTablaJugadores.addColumn("columna");
        modelTablaJugadores.addColumn("fila");
        modelTablaJugadores.addColumn("conquistador");
        modelTablaJugadores.addColumn("tipo de planeta");

        tablaPlanetas.setModel(modelTablaJugadores);
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
            if (juego.getMapa().esAlAzar()) {
                modelTablaJugadores.addRow(new Object[]{nombre, "aleatorio", "aleatorio", "aleatorio", "aleatorio", "aleatorio", jugadores[i].getNombre(), jugadores[i].getColor()});
            } else {
                modelTablaJugadores.addRow(new Object[]{nombre, 8, 8, 0.4, 5, 5, jugadores[i].getNombre(), jugadores[i].getColor()});
            }
        }
    }

    public void agregarPlaneta() {
        String nombre = generarNombre();
        if (juego.getMapa().esAlAzar()) {
            modelTablaJugadores.addRow(new Object[]{nombre, "aleatorio", "aleatorio", "aleatorio", "aleatorio", "aleatorio", jugadores[0].getNombre(), jugadores[0].getColor()});
        } else {
            modelTablaJugadores.addRow(new Object[]{nombre, 8, 8, 0.4, 5, 5, jugadores[0].getNombre(), jugadores[0].getColor()});
        }
    }

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

    public void validarConfiguraciones() throws ValidacionesException, ListaException {
        juego.getMapa().reiniciarListaPlanetas();
        if (juego.getMapa().esAlAzar()) {
            crearPlanetasNeutralesAleatoriamente();
            crearPlanetasJugadoreAleatoriamente();
        } else {
            validarConfiguracionesPlanetasJugador();
            validarConfiguracionesPlanetasNeutrales();
        }
        crearPlanetasFantasmas();
        crearPlanetasZombie();
        comenzarJuego();
    }

    public void crearPlanetasFantasmas() throws ListaException {
        int cantidadPlanetasFantasmas = juego.getMapa().getCantidadPlanetasFantasmas();
        for (int i = 0; i < cantidadPlanetasFantasmas; i++) {

            String nombre = generarNombre();

            PlanetaFantasma nuevoPlanetaFantasma = new PlanetaFantasma(nombre, juego.getMapa());
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaFantasma);
            juego.getMapa().getPlanetasFantasma().agregar(nuevoPlanetaFantasma);

        }
    }
    public void crearPlanetasZombie() throws ListaException {
        int cantidadPlanetasZombie = juego.getMapa().getCantidadPlanetasZombie();
        for (int i = 0; i < cantidadPlanetasZombie; i++) {

            String nombre = generarNombre();

            PlanetaZombie nuevoPlanetaZombie = new PlanetaZombie(nombre,juego.getMapa().getNavesAtaqueZombie(), juego.getMapa());
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaZombie);
            juego.getMapa().getPlanetasZombie().agregar(nuevoPlanetaZombie);

        }
    }

    public void crearPlanetasNeutralesAleatoriamente() throws ListaException {
        int planetasNeutrales = juego.getMapa().getTotalPlanetasNeutrales();
        for (int i = 0; i < planetasNeutrales; i++) {

            String nombre = generarNombre();
            int cantidadNaves = Planeta.crearCantidadDeNavesAleatoria();
            Posicion posicion = Planeta.crearPosiciónAleatoria(juego.getMapa());
            double porcentajeMuerte = Planeta.crearPorcentajeMuerteAleatorio();
            int produccion = Planeta.crearProduccionAleatoria();

            PlanetaNeutral nuevoPlanetaNeutral = new PlanetaNeutral(nombre, cantidadNaves, posicion, porcentajeMuerte, produccion);
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaNeutral);
            juego.getMapa().getPlanetasNeutrales().agregar(nuevoPlanetaNeutral);

        }
    }

    public void crearPlanetasJugadoreAleatoriamente() throws ListaException {
        for (int i = 0; i < modelTablaJugadores.getRowCount(); i++) {

            String nombre = modelTablaJugadores.getValueAt(i, 0).toString();
            int cantidadNaves = Planeta.crearCantidadDeNavesAleatoria();
            int produccion = Planeta.crearProduccionAleatoria();
            double porcentajeMuerte = Planeta.crearPorcentajeMuerteAleatorio();
            Posicion posicion = Planeta.crearPosiciónAleatoria(juego.getMapa());
            Jugador jugador = buscarJugador(modelTablaJugadores.getValueAt(i, 6).toString());
            String tipo = modelTablaJugadores.getValueAt(i, 7).toString();

            PlanetaJugador nuevoPlanetaJugador = new PlanetaJugador(nombre, cantidadNaves, posicion, porcentajeMuerte, produccion, jugador, tipo);
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaJugador);
            juego.getMapa().getPlanetasJugador().agregar(nuevoPlanetaJugador);
            jugador.getPlanetas().agregar(nuevoPlanetaJugador);

        }
    }

    public void validarConfiguracionesPlanetasNeutrales() throws ListaException, ValidacionesException {
        int totalPlanetas = modelTablaNeutrales.getRowCount();
        FilaPlaneta[] filasPlanetas = crearFilasPlanetaNeutral(totalPlanetas);
        validarCadaFilaPlanetaNeutral(filasPlanetas);
    }

    public FilaPlaneta[] crearFilasPlanetaNeutral(int totalPlanetas) {
        FilaPlaneta[] filasPlanetas = new FilaPlaneta[totalPlanetas];
        for (int i = 0; i < totalPlanetas; i++) {
            String nombre = modelTablaNeutrales.getValueAt(i, 0).toString();
            int cantidadNaves = Integer.parseInt(modelTablaNeutrales.getValueAt(i, 1).toString());
            int producción = Integer.parseInt(modelTablaNeutrales.getValueAt(i, 2).toString());
            double porcentajeMuertes = Double.parseDouble(modelTablaNeutrales.getValueAt(i, 3).toString());
            int columnaPosicion = Integer.parseInt(modelTablaNeutrales.getValueAt(i, 4).toString());
            int filaPosicion = Integer.parseInt(modelTablaNeutrales.getValueAt(i, 5).toString());
            filasPlanetas[i] = new FilaPlaneta(i, nombre, cantidadNaves, producción, porcentajeMuertes, columnaPosicion, filaPosicion, this, juego.getMapa());
        }
        return filasPlanetas;
    }

    public void validarCadaFilaPlanetaNeutral(FilaPlaneta[] filasPlanetas) throws ListaException, ValidacionesException {
        for (FilaPlaneta filaPlanetaJugador :
                filasPlanetas) {
            filaPlanetaJugador.validar();
            PlanetaNeutral nuevoPlanetaNeutral = new PlanetaNeutral(filaPlanetaJugador.getNombre(), filaPlanetaJugador.getCantidadNaves(), filaPlanetaJugador.getPosicion(), filaPlanetaJugador.getPorcentajeMuertes(), filaPlanetaJugador.getProducción());
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaNeutral);
            juego.getMapa().getPlanetasNeutrales().agregar(nuevoPlanetaNeutral);
        }
    }

    public void validarConfiguracionesPlanetasJugador() throws ListaException, ValidacionesException {
        int totalPlanetas = modelTablaJugadores.getRowCount();
        FilaPlanetaJugador[] filasPlanetas = crearFilasPlanetaJugador(totalPlanetas);
        validarCadaFilaPlanetaJugador(filasPlanetas);
    }

    public void validarCadaFilaPlanetaJugador(FilaPlanetaJugador[] filasPlanetas) throws ListaException, ValidacionesException {
        for (FilaPlanetaJugador filaPlanetaJugador :
                filasPlanetas) {
            filaPlanetaJugador.validar();
            Jugador jugador = buscarJugador(filaPlanetaJugador.getConquistador());
            PlanetaJugador nuevoPlanetaJugador = new PlanetaJugador(filaPlanetaJugador.getNombre(), filaPlanetaJugador.getCantidadNaves(), filaPlanetaJugador.getPosicion(), filaPlanetaJugador.getPorcentajeMuertes(), filaPlanetaJugador.getProducción(), jugador, filaPlanetaJugador.getTipo());
            juego.getMapa().getPlanetas().agregar(nuevoPlanetaJugador);
            juego.getMapa().getPlanetasJugador().agregar(nuevoPlanetaJugador);
            jugador.getPlanetas().agregar(nuevoPlanetaJugador);
        }
    }

    public FilaPlanetaJugador[] crearFilasPlanetaJugador(int totalPlanetas) {
        FilaPlanetaJugador[] filasPlanetas = new FilaPlanetaJugador[totalPlanetas];
        for (int i = 0; i < totalPlanetas; i++) {
            String nombre = modelTablaJugadores.getValueAt(i, 0).toString();
            int cantidadNaves = Integer.parseInt(modelTablaJugadores.getValueAt(i, 1).toString());
            int producción = Integer.parseInt(modelTablaJugadores.getValueAt(i, 2).toString());
            double porcentajeMuertes = Double.parseDouble(modelTablaJugadores.getValueAt(i, 3).toString());
            int columnaPosicion = Integer.parseInt(modelTablaJugadores.getValueAt(i, 4).toString());
            int filaPosicion = Integer.parseInt(modelTablaJugadores.getValueAt(i, 5).toString());
            String conquistador = modelTablaJugadores.getValueAt(i, 6).toString();
            String tipo = modelTablaJugadores.getValueAt(i, 7).toString();
            filasPlanetas[i] = new FilaPlanetaJugador(i, nombre, cantidadNaves, producción, porcentajeMuertes, columnaPosicion, filaPosicion, this, juego.getMapa(), conquistador, tipo);
        }
        return filasPlanetas;
    }

    public void comenzarJuego() throws ListaException {;
        dispose();
        parent.dispose();
        juego.empezarPartida(konquestFrame);
    }

    public Jugador buscarJugador(String nombre) {
        for (int i = 0; i < juego.getJugadores().length; i++) {
            if (juego.getJugadores()[i].getNombre().equals(nombre)) {
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

        jPanel1 = new javax.swing.JPanel();
        planetasJugadores = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPlanetas = new javax.swing.JTable();
        buttons = new javax.swing.JPanel();
        agregarPlanetaButton = new javax.swing.JButton();
        planetasNeutrales = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaNeutrales = new javax.swing.JTable();
        buttons1 = new javax.swing.JPanel();
        empezarJuegoButton = new javax.swing.JButton();
        cancelarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("planetas de jugadores");
        setPreferredSize(new java.awt.Dimension(750, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        planetasJugadores.setLayout(new javax.swing.BoxLayout(planetasJugadores, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Configuración Planetas de Jugadores");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 5, 1));
        jLabel1.setIconTextGap(10);
        jLabel1.setPreferredSize(new java.awt.Dimension(326, 52));
        planetasJugadores.add(jLabel1);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(600, 430));

        tablaPlanetas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaPlanetas.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane2.setViewportView(tablaPlanetas);

        planetasJugadores.add(jScrollPane2);

        buttons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        agregarPlanetaButton.setText("Agregar Planeta");
        agregarPlanetaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPlanetaButtonActionPerformed(evt);
            }
        });
        buttons.add(agregarPlanetaButton);

        planetasJugadores.add(buttons);

        getContentPane().add(planetasJugadores);

        planetasNeutrales.setLayout(new javax.swing.BoxLayout(planetasNeutrales, javax.swing.BoxLayout.Y_AXIS));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Configuración Planetas Neutrales");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 5, 1));
        jLabel3.setIconTextGap(10);
        jLabel3.setPreferredSize(new java.awt.Dimension(326, 52));
        planetasNeutrales.add(jLabel3);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(600, 430));

        tablaNeutrales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaNeutrales.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane4.setViewportView(tablaNeutrales);

        planetasNeutrales.add(jScrollPane4);

        getContentPane().add(planetasNeutrales);

        buttons1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        empezarJuegoButton.setText("Empezar Juego");
        empezarJuegoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empezarJuegoButtonActionPerformed(evt);
            }
        });
        buttons1.add(empezarJuegoButton);

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });
        buttons1.add(cancelarButton);

        getContentPane().add(buttons1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarPlanetaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPlanetaButtonActionPerformed
        agregarPlaneta();
    }//GEN-LAST:event_agregarPlanetaButtonActionPerformed

    private void empezarJuegoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empezarJuegoButtonActionPerformed
        try {
            validarConfiguraciones();
        } catch (ValidacionesException e) {
            e.printStackTrace();
        } catch (ListaException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_empezarJuegoButtonActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelarButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarPlanetaButton;
    private javax.swing.JPanel buttons;
    private javax.swing.JPanel buttons1;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton empezarJuegoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel planetasJugadores;
    private javax.swing.JPanel planetasNeutrales;
    private javax.swing.JTable tablaNeutrales;
    private javax.swing.JTable tablaPlanetas;
    // End of variables declaration//GEN-END:variables
}
